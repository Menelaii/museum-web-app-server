package ru.solovetskyJungs.museum.models.dto;

import ru.solovetskyJungs.museum.models.dto.fileAttachmets.FileAttachmentDTO;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

public record ArtifactShortDTO(Long id, String title, FileAttachmentDTO preview,
                               ValueCategory category, ArtifactType type) {
}
