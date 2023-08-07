package ru.solovetskyJungs.museum.dto;

import ru.solovetskyJungs.museum.entities.ArtifactType;
import ru.solovetskyJungs.museum.entities.ValueCategory;

import java.time.LocalDate;
import java.util.Map;

public record ArtifactUploadDTO(String title, LocalDate creationPeriod,
                                ArtifactType type, ValueCategory valueCategory,
                                Map<String, String> imagesDescriptions) {
}