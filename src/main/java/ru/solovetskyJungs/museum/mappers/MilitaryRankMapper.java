package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.MilitaryRankDTO;
import ru.solovetskyJungs.museum.dto.MilitaryRankUploadDTO;
import ru.solovetskyJungs.museum.entities.MilitaryRank;

@Component
@RequiredArgsConstructor
public class MilitaryRankMapper {
    private final ModelMapper modelMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

    public MilitaryRankDTO map(MilitaryRank militaryRank) {
        return new MilitaryRankDTO(
                militaryRank.getId(),
                militaryRank.getTitle(),
                fileAttachmentMapper.map(militaryRank.getImage())
        );
    }

    public MilitaryRank map(MilitaryRankUploadDTO militaryRankUploadDTO) {
        return modelMapper.map(militaryRankUploadDTO, MilitaryRank.class);
    }
}
