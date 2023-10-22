package ru.solovetskyJungs.museum.models.dto.militaryRanks;

import java.time.LocalDate;

public record MilitaryRankDetailsUploadDTO(LocalDate dateOfAssignment, Long rankId) {
}
