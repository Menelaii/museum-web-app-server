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
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.entities.BiographyAttachment;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.enums.CareerType;
import ru.solovetskyJungs.museum.repositories.BiographyRepository;
import ru.solovetskyJungs.museum.repositories.CareerDetailsRepository;
import ru.solovetskyJungs.museum.searchCriterias.BiographySearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.specifications.BiographySpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BiographiesService {
    private final BiographyRepository repository;
    private final FileAttachmentService fileAttachmentService;
    private final MedalDetailsService medalDetailsService;
    private final MilitaryRankDetailsService militaryRankDetailsService;
    private final CareerDetailsRepository careerDetailsRepository;

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
    public void save(Biography biography,
                     List<MultipartFile> images,
                     MultipartFile preview,
                     MultipartFile presentation) {

        biography.setMedalDetails(
                medalDetailsService.save(biography.getMedalDetails())
        );

        biography.setMilitaryRankDetails(
                militaryRankDetailsService.save(biography.getMilitaryRankDetails())
        );

        List<BiographyAttachment> biographyAttachments = new ArrayList<>();

        FileAttachment previewAttachment = fileAttachmentService.saveFile(preview);
        biographyAttachments.add(new BiographyAttachment(true, biography, previewAttachment));

        if (images != null && !images.isEmpty()) {
            List<FileAttachment> attachments = fileAttachmentService.saveFiles(images);
            biographyAttachments.addAll(attachments
                    .stream()
                    .map(el -> new BiographyAttachment(false, biography, el))
                    .toList());
        }

        biography.setImages(biographyAttachments);

        if (presentation != null) {
            FileAttachment presentationAttachment = fileAttachmentService.saveFile(presentation);
            biography.setPresentation(presentationAttachment);
        }

        if (biography.getMilitaryServiceDetails() != null) {
            biography.getMilitaryServiceDetails().forEach(d -> {
                d.setBiography(biography);
                d.setCareerType(CareerType.MILITARY_SERVICE);
            });
        }

        if (biography.getEmploymentHistory() != null) {
            biography.getEmploymentHistory().forEach(d -> {
                d.setBiography(biography);
                d.setCareerType(CareerType.EMPLOYMENT_HISTORY);
            });
        }

        Biography entity = repository.save(biography);

        biography.getMedalDetails().forEach((d) -> d.setBiography(entity));
        biography.getMilitaryRankDetails().forEach((d) -> d.setBiography(entity));
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }
}
