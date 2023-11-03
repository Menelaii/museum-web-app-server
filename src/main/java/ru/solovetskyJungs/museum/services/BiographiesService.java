package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.entities.*;
import ru.solovetskyJungs.museum.models.enums.CareerType;
import ru.solovetskyJungs.museum.repositories.BiographyAttachmentRepository;
import ru.solovetskyJungs.museum.repositories.BiographyRepository;
import ru.solovetskyJungs.museum.repositories.CareerDetailsRepository;
import ru.solovetskyJungs.museum.searchCriterias.BiographySearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.specifications.BiographySpecifications;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BiographiesService {
    private final BiographyRepository repository;
    private final FileAttachmentsService fileAttachmentsService;
    private final CareerDetailsRepository careerDetailsRepository;
    private final BiographyAttachmentRepository attachmentsRepository;

    public Biography getById(Long biographyId) {
        Biography biography = repository.findById(biographyId)
                .orElseThrow(EntityNotFoundException::new);

        Hibernate.initialize(biography.getImages());
        Hibernate.initialize(biography.getMedalDetails());
        Hibernate.initialize(biography.getMilitaryRankDetails());

        biography.setMilitaryServiceDetails(
                careerDetailsRepository.getCareerDetails(
                        biographyId, CareerType.MILITARY_SERVICE
                )
        );

        biography.setEmploymentHistory(
                careerDetailsRepository.getCareerDetails(
                        biographyId, CareerType.EMPLOYMENT_HISTORY
                )
        );

        return biography;
    }

    public Page<Biography> findAllWithFilters(XPage page, BiographySearchCriteria searchCriteria) {
        Specification<Biography> spec = Specification.where(BiographySpecifications.withShortSelect());

        if (searchCriteria.getSurname() != null && !searchCriteria.getSurname().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withSurname(searchCriteria.getSurname()));
        }

        if (searchCriteria.getName() != null && !searchCriteria.getName().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withName(searchCriteria.getName()));
        }

        if (searchCriteria.getPatronymic() != null && !searchCriteria.getPatronymic().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withPatronymic(searchCriteria.getPatronymic()));
        }

        if (searchCriteria.getDateOfBirth() != null) {
            spec = spec.and(BiographySpecifications.withDateOfBirth(searchCriteria.getDateOfBirth()));
        }

        if (searchCriteria.getDateOfDeath() != null) {
            spec = spec.and(BiographySpecifications.withDateOfDeath(searchCriteria.getDateOfDeath()));
        }

        if (searchCriteria.getPlaceOfBirth() != null && !searchCriteria.getPlaceOfBirth().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withPlaceOfBirth(searchCriteria.getPlaceOfBirth()));
        }

        if (searchCriteria.getPlaceOfDeath() != null && !searchCriteria.getPlaceOfDeath().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withPlaceOfDeath(searchCriteria.getPlaceOfDeath()));
        }

        if (searchCriteria.getMedal() != null && !searchCriteria.getMedal().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withMedal(searchCriteria.getMedal()));
        }

        if (searchCriteria.getMilitaryRank() != null && !searchCriteria.getMilitaryRank().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withMilitaryRank(searchCriteria.getMilitaryRank()));
        }

        if (searchCriteria.getPlaceOfService() != null && !searchCriteria.getPlaceOfService().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withPlaceOfMilitaryService(searchCriteria.getPlaceOfService()));
        }

        if (searchCriteria.getPlaceOfEmployment() != null && !searchCriteria.getPlaceOfEmployment().trim().isEmpty()) {
            spec = spec.and(BiographySpecifications.withPlaceOfEmployment(searchCriteria.getPlaceOfEmployment()));
        }

        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());

        return repository.findAll(spec, pageable);
    }

    @Transactional
    public void save(Biography biography, List<MultipartFile> images, MultipartFile preview, MultipartFile presentation) {
        List<BiographyAttachment> biographyAttachments = new ArrayList<>();

        FileAttachment previewAttachment = fileAttachmentsService.saveAsJPG(preview);
        biographyAttachments.add(new BiographyAttachment(true, biography, previewAttachment));

        if (images != null && !images.isEmpty()) {
            List<FileAttachment> attachments = fileAttachmentsService.saveImagesAsJPG(images);
            biographyAttachments.addAll(
                    attachments.stream()
                            .map(el -> new BiographyAttachment(false, biography, el))
                            .toList()
            );
        }

        biography.setImages(biographyAttachments);

        if (presentation != null) {
            FileAttachment presentationAttachment = fileAttachmentsService.saveFile(presentation);
            biography.setPresentation(presentationAttachment);
        }

        repository.save(biography);
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }

    @Transactional
    public void editPresentation(Long id, MultipartFile presentation) {
        Biography biography = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        FileAttachment oldPresentation = biography.getPresentation();
        if (oldPresentation != null) {
            fileAttachmentsService.delete(oldPresentation);
        }

        FileAttachment newPresentation = fileAttachmentsService.saveFile(presentation);
        biography.setPresentation(newPresentation);

        repository.save(biography);
    }

    @Transactional
    public void addImage(Long id, MultipartFile image, Boolean isPreview) {
        Biography biography = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        isPreview = isPreview != null ? isPreview : false;
        if (isPreview) {
            BiographyAttachment oldPreview = findPreview(biography);
            attachmentsRepository.unsetAsPreview(oldPreview.getId());
        }

        FileAttachment fileAttachment = fileAttachmentsService.saveAsJPG(image);
        BiographyAttachment imageAttachment = new BiographyAttachment(isPreview, biography, fileAttachment);

        biography.getImages().add(imageAttachment);

        repository.save(biography);
    }

    @Transactional
    public void deleteImage(Long biographyId, Long imageId) {
        Biography biography = repository.findById(biographyId)
                .orElseThrow(EntityNotFoundException::new);

        BiographyAttachment imageToRemove = biography.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        biography.getImages().remove(imageToRemove);
        imageToRemove.setBiography(null);
        fileAttachmentsService.delete(imageToRemove.getFileAttachment());

        repository.save(biography);
    }

    @Transactional
    public void changePreview(Long id, Long imageId) {
        Biography biography = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (!attachmentsRepository.existsById(imageId)) {
            throw new EntityNotFoundException("Вложение не найдено");
        }

        BiographyAttachment oldPreview = findPreview(biography);
        if (oldPreview != null) {
            attachmentsRepository.unsetAsPreview(oldPreview.getId());
        }

        attachmentsRepository.setAsPreview(imageId);

        repository.save(biography);
    }

    public BiographyAttachment findPreview(Biography biography) {
        return biography.getImages().stream()
                .filter(BiographyAttachment::isPreview)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }


    @Transactional
    public void edit(Long id, Biography updatedBiography) {
        Biography existingBiography = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        existingBiography.setSurname(updatedBiography.getSurname());
        existingBiography.setName(updatedBiography.getName());
        existingBiography.setPatronymic(updatedBiography.getPatronymic());
        existingBiography.setBirthDate(updatedBiography.getBirthDate());
        existingBiography.setPlaceOfBirth(updatedBiography.getPlaceOfBirth());
        existingBiography.setDateOfDeath(updatedBiography.getDateOfDeath());
        existingBiography.setPlaceOfDeath(updatedBiography.getPlaceOfDeath());

        existingBiography.setMedalDetails(updatedBiography.getMedalDetails());
        existingBiography.setMilitaryRankDetails(updatedBiography.getMilitaryRankDetails());

        existingBiography.setMilitaryServiceDetails(updatedBiography.getMilitaryServiceDetails());
        existingBiography.setEmploymentHistory(updatedBiography.getEmploymentHistory());

        repository.save(existingBiography);
    }
}
