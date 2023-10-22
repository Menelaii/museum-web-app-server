package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.models.dto.CareerDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.biographies.BiographyDTO;
import ru.solovetskyJungs.museum.models.dto.biographies.BiographyShortDTO;
import ru.solovetskyJungs.museum.models.dto.biographies.BiographyUploadDTO;
import ru.solovetskyJungs.museum.models.dto.fileAttachmets.ImageAttachmentDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.medals.MedalDetailsUploadDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankDetailsDTO;
import ru.solovetskyJungs.museum.models.dto.militaryRanks.MilitaryRankDetailsUploadDTO;
import ru.solovetskyJungs.museum.models.entities.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BiographyMapper {
    private final ModelMapper modelMapper;
    private final MedalMapper medalMapper;
    private final MilitaryRankMapper militaryRankMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

    public BiographyShortDTO mapToShort(Biography biography) {
        return new BiographyShortDTO(
          biography.getId(),
          biography.getSurname(),
          biography.getName(),
          biography.getPatronymic(),
          biography.getBirthDate(),
          biography.getDateOfDeath()
        );
    }

    public BiographyDTO map(Biography biography) {
        return new BiographyDTO(
                biography.getId(),
                biography.getSurname(),
                biography.getName(),
                biography.getPatronymic(),
                biography.getBirthDate(),
                biography.getPlaceOfBirth(),
                biography.getDateOfDeath(),
                biography.getPlaceOfDeath(),
                biography.getMedalDetails()
                        .stream()
                        .map(this::toMedalDetailsDTO)
                        .toList(),
                biography.getMilitaryRankDetails()
                        .stream()
                        .map(this::toMilitaryRankDetailsDTO)
                        .toList(),
                biography.getMilitaryServiceDetails()
                        .stream()
                        .map(this::toCareerDetailsDTO)
                        .toList(),
                biography.getEmploymentHistory()
                        .stream()
                        .map(this::toCareerDetailsDTO)
                        .toList(),
                biography.getImages()
                        .stream()
                        .map(this::toImageAttachmentDTO)
                        .toList(),
                fileAttachmentMapper.map(biography.getPresentation())
        );
    }

    public Biography map(BiographyUploadDTO biographyUploadDTO) {

        Biography biography = modelMapper.map(biographyUploadDTO, Biography.class);

        List<MedalDetails> medalDetails = biographyUploadDTO
                .medalDetails()
                .stream()
                .map(this::toMedalDetails)
                .toList();

        List<MilitaryRankDetails> militaryRankDetails = biographyUploadDTO
                .militaryRankDetails()
                .stream()
                .map(this::toMilitaryRankDetails)
                .toList();

        biography.setMedalDetails(medalDetails);
        biography.setMilitaryRankDetails(militaryRankDetails);

        return biography;
    }

    private MedalDetails toMedalDetails(MedalDetailsUploadDTO uploadDTO) {
        MedalDetails medalDetails = modelMapper.map(uploadDTO, MedalDetails.class);
        medalDetails.setMedal(new Medal(uploadDTO.medalId()));

        return medalDetails;
    }

    private MilitaryRankDetails toMilitaryRankDetails(MilitaryRankDetailsUploadDTO uploadDTO) {
        MilitaryRankDetails militaryRankDetails = modelMapper.map(uploadDTO, MilitaryRankDetails.class);
        militaryRankDetails.setRank(new MilitaryRank(uploadDTO.rankId()));

        return militaryRankDetails;
    }

    private MedalDetailsDTO toMedalDetailsDTO(MedalDetails details) {
        return new MedalDetailsDTO(
                details.getDateOfAward(),
                details.getPlaceOfAward(),
                medalMapper.map(details.getMedal())
        );
    }

    private MilitaryRankDetailsDTO toMilitaryRankDetailsDTO(MilitaryRankDetails details) {
        return new MilitaryRankDetailsDTO(
                details.getDateOfAssignment(),
                militaryRankMapper.map(details.getRank())
        );
    }

    private CareerDetailsDTO toCareerDetailsDTO(CareerDetails careerDetails) {
        return modelMapper.map(careerDetails, CareerDetailsDTO.class);
    }

    private ImageAttachmentDTO toImageAttachmentDTO(BiographyAttachment biographyAttachment) {
        return fileAttachmentMapper.map(biographyAttachment);
    }
}
