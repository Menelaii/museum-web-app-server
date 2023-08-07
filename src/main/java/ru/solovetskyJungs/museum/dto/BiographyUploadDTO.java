package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record BiographyUploadDTO(
        String surname,
        String name,
        String patronymic,
        LocalDate birthDate,
        String placeOfBirth,
        LocalDate dateOfDeath,
        String placeOfDeath,
        List<MedalDetailsUploadDTO> medalDetails,
        List<MilitaryRankDetailsUploadDTO> militaryRankDetails,
        List<CareerDetailsDTO> militaryServiceDetails,
        List<CareerDetailsDTO> employmentHistory,
        Map<String, String> imagesDescriptions) {
}