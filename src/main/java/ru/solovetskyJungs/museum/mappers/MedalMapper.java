package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.MedalDTO;
import ru.solovetskyJungs.museum.dto.MedalUploadDTO;
import ru.solovetskyJungs.museum.entities.Medal;

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

    public Medal map(MedalUploadDTO medalUploadDTO) {
        return modelMapper.map(medalUploadDTO, Medal.class);
    }
}
