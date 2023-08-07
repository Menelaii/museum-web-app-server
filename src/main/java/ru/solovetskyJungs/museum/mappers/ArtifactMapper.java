package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.ArtifactDTO;
import ru.solovetskyJungs.museum.dto.ArtifactUploadDTO;
import ru.solovetskyJungs.museum.dto.FileAttachmentDTO;
import ru.solovetskyJungs.museum.entities.Artifact;
import ru.solovetskyJungs.museum.entities.ArtifactAttachment;

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
                        .map(this::toFileAttachmentDTO)
                        .collect(Collectors.toList())
        );
    }

    public Artifact map(ArtifactUploadDTO artifactUploadDTO) {
        return modelMapper.map(artifactUploadDTO, Artifact.class);
    }

    private FileAttachmentDTO toFileAttachmentDTO(ArtifactAttachment artifactAttachment) {
        return fileAttachmentMapper.map(artifactAttachment.getFileAttachment());
    }
}
