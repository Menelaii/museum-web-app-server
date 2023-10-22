package ru.solovetskyJungs.museum.models.dto.medals;

import java.time.LocalDate;

public record MedalDetailsUploadDTO(
        LocalDate dateOfAward,
        String placeOfAward,
        Long medalId) {
}
