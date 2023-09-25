package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.ImageAttachmentDTO;
import ru.solovetskyJungs.museum.dto.FileAttachmentDTO;
import ru.solovetskyJungs.museum.entities.ArtifactAttachment;
import ru.solovetskyJungs.museum.entities.BiographyAttachment;
import ru.solovetskyJungs.museum.entities.FileAttachment;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class FileAttachmentMapper {

    @Value("${backendURL}")
    private String backendURL;

    public FileAttachmentDTO map(FileAttachment fileAttachment) {
        if (fileAttachment == null) {
            return null;
        }

        return new FileAttachmentDTO(
                fileAttachment.getId(),
                pathToURL(fileAttachment.getUri())
        );
    }

    public ImageAttachmentDTO map(ArtifactAttachment attachment) {
        return new ImageAttachmentDTO(
                attachment.getId(),
                pathToURL(attachment.getFileAttachment().getUri()),
                attachment.isPreview()
        );
    }

    public ImageAttachmentDTO map(BiographyAttachment attachment) {
        return new ImageAttachmentDTO(
                attachment.getId(),
                pathToURL(attachment.getFileAttachment().getUri()),
                attachment.isPreview()
        );
    }

    private String pathToURL(String path) {
        return backendURL +
                "/files/" +
                Path.of(path).getFileName();
    }
}
