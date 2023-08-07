package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.FileAttachmentDTO;
import ru.solovetskyJungs.museum.entities.FileAttachment;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class FileAttachmentMapper {

    @Value("${backendURL}")
    private String backendURL;

    public FileAttachmentDTO map(FileAttachment fileAttachment) {
        return new FileAttachmentDTO(
                pathToURL(fileAttachment.getUri()),
                fileAttachment.getDescription()
        );
    }

    private String pathToURL(String path) {
        return backendURL +
                "/files/" +
                Path.of(path).getFileName();
    }
}
