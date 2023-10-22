package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.entities.FileAttachment;
import ru.solovetskyJungs.museum.models.entities.Medal;
import ru.solovetskyJungs.museum.models.entities.projections.MedalProjection;
import ru.solovetskyJungs.museum.repositories.MedalRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedalService {
    private final MedalRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentsService fileAttachmentsService;

    public List<Medal> getAll() {
        return repository.findAll();
    }

    public Medal getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Medal medal, MultipartFile image) {
        FileAttachment fileAttachment = fileAttachmentsService.saveFile(image);
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

    @Transactional
    public void edit(Long id, Medal updatedMedal) {
        Medal medalToUpdate = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        medalToUpdate.setTitle(updatedMedal.getTitle());
        medalToUpdate.setDescription(updatedMedal.getDescription());

        repository.save(medalToUpdate);
    }

    @Transactional
    public void changePreview(Long id, MultipartFile image) {
        Medal medalToUpdate = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        FileAttachment preview = medalToUpdate.getImage();
        if (preview != null) {
            fileAttachmentsService.delete(preview);
        }

        FileAttachment newImage = fileAttachmentsService.saveFile(image);
        medalToUpdate.setImage(newImage);

        repository.save(medalToUpdate);
    }

    public List<String> getTitles() {
        return repository.findTitles();
    }

    public List<MedalProjection> getAllWithShortSelect() {
        return repository.findAllWithShortSelect();
    }
}
