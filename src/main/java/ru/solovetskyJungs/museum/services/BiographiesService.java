package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.entities.BiographyAttachment;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.repositories.BiographyRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BiographiesService {
    private final BiographyRepository repository;
    private final FileAttachmentService fileAttachmentService;
    private final MedalDetailsService medalDetailsService;
    private final MilitaryRankDetailsService militaryRankDetailsService;

    public Optional<Biography> getById(Long biographyId) {
        return repository.findById(biographyId);
    }

    public List<Biography> getAll() {
        List<Biography> biographies = repository.findAll();
        biographies.forEach(biography -> {
            Hibernate.initialize(biography.getImages());
            Hibernate.initialize(biography.getMedalDetails());
            Hibernate.initialize(biography.getMilitaryRankDetails());
            Hibernate.initialize(biography.getMilitaryServiceDetails());
            Hibernate.initialize(biography.getEmploymentHistory());
        });

        return biographies;
    }

    @Transactional
    public void save(Biography biography,
                     List<MultipartFile> images,
                     Map<String, String> imagesDescriptions,
                     MultipartFile presentation) {

        biography.setMedalDetails(
                medalDetailsService.save(biography.getMedalDetails())
        );

        biography.setMilitaryRankDetails(
                militaryRankDetailsService.save(biography.getMilitaryRankDetails())
        );

        List<FileAttachment> attachments =
                fileAttachmentService.saveFiles(images, imagesDescriptions);

        List<BiographyAttachment> biographyAttachments = attachments
                .stream()
                .map(el -> new BiographyAttachment(biography, el))
                .toList();

        biography.setImages(biographyAttachments);

        FileAttachment presentationAttachment = fileAttachmentService.saveFile(
                        presentation,
                        imagesDescriptions.getOrDefault(presentation.getName(), null)
        );

        biography.setPresentation(presentationAttachment);

        biography.getMilitaryServiceDetails().forEach(d -> d.setBiography(biography));
        biography.getEmploymentHistory().forEach(d -> d.setBiography(biography));

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
