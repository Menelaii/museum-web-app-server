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
import ru.solovetskyJungs.museum.models.entities.Artifact;
import ru.solovetskyJungs.museum.models.entities.ArtifactAttachment;
import ru.solovetskyJungs.museum.models.entities.FileAttachment;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;
import ru.solovetskyJungs.museum.repositories.ArtifactRepository;
import ru.solovetskyJungs.museum.searchCriterias.ArtifactsSearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.specifications.ArtifactSpecifications;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtifactService {
    private final ArtifactRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentsService fileAttachmentsService;

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

        artifacts.forEach(artifact -> Hibernate.initialize(artifact.getImages()));

        return artifacts;
    }

    public Artifact getById(Long id) {
        return repository.findByIdWithImages(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Artifact artifact, List<MultipartFile> images, MultipartFile preview) {
        List<ArtifactAttachment> artifactAttachments = new ArrayList<>();

        FileAttachment previewAttachment = fileAttachmentsService.saveFile(preview);
        artifactAttachments.add(new ArtifactAttachment(true, artifact, previewAttachment));

        if (images != null && !images.isEmpty()) {
            List<FileAttachment> attachments = fileAttachmentsService.saveFiles(images);
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

    @Transactional
    public void edit(Long id, Artifact updatedArtifact) {
        Artifact artifact = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        artifact.setTitle(updatedArtifact.getTitle());
        artifact.setCreationPeriod(updatedArtifact.getCreationPeriod());
        artifact.setType(updatedArtifact.getType());
        artifact.setValueCategory(updatedArtifact.getValueCategory());

        repository.save(artifact);
    }

    @Transactional
    public void changePreview(Long id, MultipartFile preview) {
        Artifact artifact = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        List<ArtifactAttachment> artifactAttachments = artifact.getImages();

        ArtifactAttachment oldPreview =
                artifactAttachments
                        .stream()
                        .filter(ArtifactAttachment::isPreview)
                        .findFirst().orElse(null);

        if (oldPreview != null) {
            artifactAttachments.remove(oldPreview);
            oldPreview.setArtifact(null);
            fileAttachmentsService.delete(oldPreview.getFileAttachment());
        }

        FileAttachment fileAttachment =
                fileAttachmentsService.saveFile(preview);

        artifactAttachments.add(
                new ArtifactAttachment(
                        true,
                        artifact,
                        fileAttachment
                )
        );

        repository.save(artifact);
    }

    @Transactional
    public void addImage(Long id, MultipartFile image) {
        Artifact artifact = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        FileAttachment fileAttachment = fileAttachmentsService.saveFile(image);
        ArtifactAttachment artifactAttachment = new ArtifactAttachment(false, artifact, fileAttachment);

        artifact.getImages().add(artifactAttachment);

        repository.save(artifact);
    }

    @Transactional
    public void deleteImage(Long artifactId, Long imageId) {
        Artifact artifact = repository.findById(artifactId)
                .orElseThrow(EntityNotFoundException::new);

        ArtifactAttachment imageToRemove = artifact.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        artifact.getImages().remove(imageToRemove);
        imageToRemove.setArtifact(null);
        fileAttachmentsService.delete(imageToRemove.getFileAttachment());

        repository.save(artifact);
    }
}
