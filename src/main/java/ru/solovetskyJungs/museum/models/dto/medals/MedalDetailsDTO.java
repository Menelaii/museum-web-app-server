package ru.solovetskyJungs.museum.models.dto.medals;

import java.time.LocalDate;

public record MedalDetailsDTO(
        LocalDate dateOfAward,
        String placeOfAward,
        MedalDTO medal) {
}
