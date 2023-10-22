package ru.solovetskyJungs.museum.models.dto.biographies;

import ru.solovetskyJungs.museum.models.dto.CareerDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.fileAttachmets.FileAttachmentDTO;
import ru.solovetskyJungs.museum.models.dto.fileAttachmets.ImageAttachmentDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankDetailsDTO;

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
        List<ImageAttachmentDTO> images,
        FileAttachmentDTO presentation) {
}
