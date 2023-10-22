package ru.solovetskyJungs.museum.models.dto.biographies;

import java.time.LocalDate;

public record BiographyShortDTO(Long id, String surname, String name, String patronymic,
                                LocalDate birthDate, LocalDate dateOfDeath) {
}
