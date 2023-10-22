package ru.solovetskyJungs.museum.models.dto.articles;

import ru.solovetskyJungs.museum.models.dto.fileAttachmets.ImageAttachmentDTO;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

import java.time.LocalDate;
import java.util.List;

public record ArtifactDTO(Long id, String title, LocalDate creationPeriod,
                          ArtifactType type, ValueCategory valueCategory,
                          List<ImageAttachmentDTO> images) {
}
