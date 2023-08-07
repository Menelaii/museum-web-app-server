package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.FileAttachment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class FileAttachmentService {
    private final FileStorageService fileStorageService;

    public List<FileAttachment> saveFiles(List<MultipartFile> files, Map<String, String> descriptions) {
        List<FileAttachment> fileAttachments = new ArrayList<>();
        for (MultipartFile file: files) {
            FileAttachment fileAttachment =
                    saveFile(file, descriptions.getOrDefault(file.getName(), null));

            fileAttachments.add(fileAttachment);
        }

        return fileAttachments;
    }

    public FileAttachment saveFile(MultipartFile file, String description) {
        String path = fileStorageService.save(file);
        return new FileAttachment(path, description);
    }
}
