package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.FileAttachment;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileAttachmentService {
    private final FileStorageService fileStorageService;

    public List<FileAttachment> saveFiles(List<MultipartFile> files) {
        List<FileAttachment> fileAttachments = new ArrayList<>();
        for (MultipartFile file: files) {
            fileAttachments.add(saveFile(file));
        }

        return fileAttachments;
    }

    public FileAttachment saveFile(MultipartFile file) {
        String path = fileStorageService.save(file);
        return new FileAttachment(path);
    }
}
