package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.entities.FileAttachment;
import ru.solovetskyJungs.museum.repositories.FileAttachmentsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileAttachmentsService {
    private final FileStorageService fileStorageService;
    private final FileAttachmentsRepository repository;

    public List<FileAttachment> saveImages(List<MultipartFile> files) {
        List<FileAttachment> fileAttachments = new ArrayList<>();
        for (MultipartFile file: files) {
            fileAttachments.add(saveImage(file));
        }

        return fileAttachments;
    }

    public FileAttachment saveImage(MultipartFile file) {
        String path = fileStorageService.saveImage(file);
        return new FileAttachment(path);
    }

    public FileAttachment saveFile(MultipartFile file) {
        String path = fileStorageService.save(file);
        return new FileAttachment(path);
    }

    @Transactional
    public void delete(FileAttachment fileAttachment) {
        fileStorageService.deleteFile(fileAttachment.getUri());
        repository.delete(fileAttachment);
    }
}
