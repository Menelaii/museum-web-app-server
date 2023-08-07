package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record MedalDetailsUploadDTO(
        LocalDate dateOfAward,
        String placeOfAward,
        Long medalId) {
}
