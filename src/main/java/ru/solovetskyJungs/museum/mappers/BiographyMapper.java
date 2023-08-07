package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.*;
import ru.solovetskyJungs.museum.entities.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BiographyMapper {
    private final ModelMapper modelMapper;
    private final MedalMapper medalMapper;
    private final MilitaryRankMapper militaryRankMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

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
                        .map(this::toFileAttachmentDTO)
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

    private FileAttachmentDTO toFileAttachmentDTO(BiographyAttachment biographyAttachment) {
        return fileAttachmentMapper.map(biographyAttachment.getFileAttachment());
    }
}
