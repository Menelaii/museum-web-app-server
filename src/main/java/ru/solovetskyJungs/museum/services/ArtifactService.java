package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.entities.ArtifactAttachment;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.repositories.ArtifactRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtifactService {
    private final ArtifactRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentService fileAttachmentService;

    public List<Artifact> getAll() {
        return repository.findAllWithImages();
    }

    @Transactional
    public void create(Artifact artifact, List<MultipartFile> images,
                       Map<String, String> imagesDescriptions) {

        List<FileAttachment> attachments =
                fileAttachmentService.saveFiles(images, imagesDescriptions);

        List<ArtifactAttachment> artifactAttachments = attachments
                .stream()
                .map(el -> new ArtifactAttachment(artifact, el))
                .toList();

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
