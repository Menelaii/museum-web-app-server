package ru.solovetskyJungs.museum.models.dto.militaryRanks;

import java.time.LocalDate;

public record MilitaryRankDetailsDTO(LocalDate dateOfAssignment, MilitaryRankDTO rank) {
}
