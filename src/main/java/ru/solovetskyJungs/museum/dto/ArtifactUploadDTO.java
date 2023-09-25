package ru.solovetskyJungs.museum.dto;

import ru.solovetskyJungs.museum.enums.ArtifactType;
import ru.solovetskyJungs.museum.enums.ValueCategory;

import java.time.LocalDate;

public record ArtifactUploadDTO(String title, LocalDate creationPeriod,
                                ArtifactType type, ValueCategory valueCategory) {
}