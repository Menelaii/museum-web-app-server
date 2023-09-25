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
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.entities.ArtifactAttachment;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.enums.ArtifactType;
import ru.solovetskyJungs.museum.enums.ValueCategory;
import ru.solovetskyJungs.museum.repositories.ArtifactRepository;
import ru.solovetskyJungs.museum.searchCriterias.ArtifactsSearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.specifications.ArtifactSpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtifactService {
    private final ArtifactRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentService fileAttachmentService;

    public Page<Artifact> findAllWithFilters(XPage page, ArtifactsSearchCriteria searchCriteria) {
        Specification<Artifact> spec = Specification.where(ArtifactSpecifications.withShortSelect());

        if (searchCriteria.getTitle() != null && !searchCriteria.getTitle().isBlank()) {
            spec = spec.and(ArtifactSpecifications.withTitle(searchCriteria.getTitle()));
        }

        if (searchCriteria.getArtifactType() != null
                && searchCriteria.getArtifactType() != ArtifactType.UNDEFINED) {
            spec = spec.and(ArtifactSpecifications.withType(searchCriteria.getArtifactType()));
        }

        if (searchCriteria.getValueCategory() != null
                && searchCriteria.getValueCategory() != ValueCategory.UNDEFINED) {
            spec = spec.and(ArtifactSpecifications.withCategory(searchCriteria.getValueCategory()));
        }

        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());

        Page<Artifact> artifacts = repository.findAll(spec, pageable);

        artifacts.forEach(artifact -> {
            Hibernate.initialize(artifact.getImages());
        });

        return artifacts;
    }

    public Artifact getById(Long id) {
        return repository.findByIdWithImages(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Artifact artifact, List<MultipartFile> images, MultipartFile preview) {
        List<ArtifactAttachment> artifactAttachments = new ArrayList<>();

        FileAttachment previewAttachment = fileAttachmentService.saveFile(preview);
        artifactAttachments.add(new ArtifactAttachment(true, artifact, previewAttachment));

        if (images != null && !images.isEmpty()) {
            List<FileAttachment> attachments = fileAttachmentService.saveFiles(images);
            artifactAttachments.addAll(attachments
                    .stream()
                    .map(el -> new ArtifactAttachment(false, artifact, el))
                    .toList());
        }

        artifact.setImages(artifactAttachments);

        repository.save(artifact);
    }

    @Transactional
    public void deleteArtifact(Long id) {
        Artifact artifactToDelete =
                repository.findById(id).orElseThrow(EntityNotFoundException::new);

        List<String> paths = artifactToDelete
                .getImages()
                .stream()
                .map(el -> el.getFileAttachment().getUri())
                .toList();

        fileStorageService.deleteFiles(paths);

        repository.delete(artifactToDelete);
    }
}
