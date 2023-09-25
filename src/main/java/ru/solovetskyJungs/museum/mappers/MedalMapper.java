package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.MedalDTO;
import ru.solovetskyJungs.museum.dto.MedalShortDTO;
import ru.solovetskyJungs.museum.dto.MedalUploadDTO;
import ru.solovetskyJungs.museum.entities.Medal;
import ru.solovetskyJungs.museum.entities.projections.MedalProjection;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MedalMapper {
    private final ModelMapper modelMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

    public MedalDTO map(Medal medal) {
        return new MedalDTO(
                medal.getId(),
                medal.getTitle(),
                medal.getDescription(),
                fileAttachmentMapper.map(medal.getImage())
        );
    }

    public MedalShortDTO toShortDTO(Medal medal) {
        return new MedalShortDTO(
                medal.getId(),
                medal.getTitle()
        );
    }

    public Medal map(MedalUploadDTO medalUploadDTO) {
        return modelMapper.map(medalUploadDTO, Medal.class);
    }

    public List<MedalShortDTO> map(List<MedalProjection> projections) {
        return projections
                .stream()
                .map(this::map)
                .toList();
    }

    public MedalShortDTO map(MedalProjection projection) {
        return new MedalShortDTO(
                projection.getId(),
                projection.getTitle()
        );
    }
}
