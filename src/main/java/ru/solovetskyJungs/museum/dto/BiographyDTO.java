package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;
import java.util.List;

public record BiographyDTO(
        Long id,
        String surname,
        String name,
        String patronymic,
        LocalDate birthDate,
        String placeOfBirth,
        LocalDate dateOfDeath,
        String placeOfDeath,
        List<MedalDetailsDTO> medalDetails,
        List<MilitaryRankDetailsDTO> militaryRankDetails,
        List<CareerDetailsDTO> militaryServiceDetails,
        List<CareerDetailsDTO> employmentHistory,
        List<FileAttachmentDTO> images,
        FileAttachmentDTO presentation) {
}
