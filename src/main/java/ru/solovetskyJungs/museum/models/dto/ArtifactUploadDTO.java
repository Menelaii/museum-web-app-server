package ru.solovetskyJungs.museum.models.dto;

import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

import java.time.LocalDate;

public record ArtifactUploadDTO(String title, LocalDate creationPeriod,
                                ArtifactType type, ValueCategory valueCategory) {
}