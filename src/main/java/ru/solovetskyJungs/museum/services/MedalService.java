package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.Medal;
import ru.solovetskyJungs.museum.entities.projections.MedalProjection;
import ru.solovetskyJungs.museum.repositories.MedalRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedalService {
    private final MedalRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentService fileAttachmentService;

    public List<Medal> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Medal medal, MultipartFile image) {
        FileAttachment fileAttachment = fileAttachmentService.saveFile(image);
        medal.setImage(fileAttachment);
        repository.save(medal);
    }

    @Transactional
    public void delete(Long id) {
        Medal medalToDelete = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        FileAttachment image = medalToDelete.getImage();
        if (image != null) {
            fileStorageService.deleteFile(image.getUri());
        }

        repository.delete(medalToDelete);
    }

    public Optional<Medal> getById(Long medalId) {
        return repository.findById(medalId);
    }

    public List<String> getTitles() {
        return repository.findTitles();
    }

    public List<MedalProjection> getAllWithShortSelect() {
        return repository.findAllWithShortSelect();
    }
}
