package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record BiographyShortDTO(Long id, String surname, String name, String patronymic,
                                LocalDate birthDate, LocalDate dateOfDeath) {
}
