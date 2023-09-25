package ru.solovetskyJungs.museum.dto;

import ru.solovetskyJungs.museum.enums.ArtifactType;
import ru.solovetskyJungs.museum.enums.ValueCategory;

import java.time.LocalDate;
import java.util.List;

public record ArtifactDTO(Long id, String title, LocalDate creationPeriod,
                          ArtifactType type, ValueCategory valueCategory,
                          List<ImageAttachmentDTO> images) {
}
