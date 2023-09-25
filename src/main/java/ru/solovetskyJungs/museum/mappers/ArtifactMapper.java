package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.*;
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.entities.ArtifactAttachment;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArtifactMapper {
    private final ModelMapper modelMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

    public ArtifactDTO map(Artifact artifact) {
        return new ArtifactDTO(
                artifact.getId(),
                artifact.getTitle(),
                artifact.getCreationPeriod(),
                artifact.getType(),
                artifact.getValueCategory(),
                artifact.getImages().stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
    }

    public ArtifactShortDTO mapToShort(Artifact artifact) {
        return new ArtifactShortDTO(
          artifact.getId(),
          artifact.getTitle(),
          getPreview(artifact.getImages()),
          artifact.getValueCategory(),
          artifact.getType()
        );
    }

    public Artifact map(ArtifactUploadDTO artifactUploadDTO) {
        return modelMapper.map(artifactUploadDTO, Artifact.class);
    }

    private ImageAttachmentDTO map(ArtifactAttachment artifactAttachment) {
        return fileAttachmentMapper.map(artifactAttachment);
    }

    private FileAttachmentDTO mapToFileAttachment(ArtifactAttachment artifactAttachment) {
        return fileAttachmentMapper.map(artifactAttachment.getFileAttachment());
    }

    private FileAttachmentDTO getPreview(List<ArtifactAttachment> attachments) {
        ArtifactAttachment previewAttachment = attachments
                .stream()
                .filter(ArtifactAttachment::isPreview)
                .findFirst().orElseThrow(IllegalStateException::new);

        return mapToFileAttachment(previewAttachment);
    }
}
