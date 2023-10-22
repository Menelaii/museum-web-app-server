package ru.solovetskyJungs.museum.models.dto.biographies;

import ru.solovetskyJungs.museum.models.dto.CareerDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalDetailsUploadDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankDetailsUploadDTO;

import java.time.LocalDate;
import java.util.List;

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
        List<CareerDetailsDTO> employmentHistory) {
}
