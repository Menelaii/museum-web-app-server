package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record MilitaryRankDetailsUploadDTO(LocalDate dateOfAssignment, Long rankId) {
}
