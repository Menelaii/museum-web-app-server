package ru.solovetskyJungs.museum.dto;

import ru.solovetskyJungs.museum.enums.ArtifactType;
import ru.solovetskyJungs.museum.enums.ValueCategory;

public record ArtifactShortDTO(Long id, String title, FileAttachmentDTO preview,
                               ValueCategory category, ArtifactType type) {
}
