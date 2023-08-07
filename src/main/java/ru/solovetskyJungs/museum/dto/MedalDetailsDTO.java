package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record MedalDetailsDTO(
        LocalDate dateOfAward,
        String placeOfAward,
        MedalDTO medal) {
}
