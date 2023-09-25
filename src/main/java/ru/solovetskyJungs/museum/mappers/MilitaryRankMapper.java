package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.MilitaryRankDTO;
import ru.solovetskyJungs.museum.dto.MilitaryRankShortDTO;
import ru.solovetskyJungs.museum.dto.MilitaryRankUploadDTO;
import ru.solovetskyJungs.museum.entities.MilitaryRank;
import ru.solovetskyJungs.museum.entities.projections.MilitaryRankProjection;

import java.util.List;

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

    public MilitaryRankShortDTO toShortDTO(MilitaryRank rank) {
        return new MilitaryRankShortDTO(
                rank.getId(),
                rank.getTitle()
        );
    }

    public MilitaryRank map(MilitaryRankUploadDTO militaryRankUploadDTO) {
        return modelMapper.map(militaryRankUploadDTO, MilitaryRank.class);
    }

    public List<MilitaryRankShortDTO> map(List<MilitaryRankProjection> projections) {
        return projections
                .stream()
                .map(this::map)
                .toList();
    }

    public MilitaryRankShortDTO map(MilitaryRankProjection projection) {
        return new MilitaryRankShortDTO(
                projection.getId(),
                projection.getTitle()
        );
    }
}
