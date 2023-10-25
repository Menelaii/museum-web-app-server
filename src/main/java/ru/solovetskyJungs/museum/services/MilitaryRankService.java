package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.entities.FileAttachment;
import ru.solovetskyJungs.museum.models.entities.MilitaryRank;
import ru.solovetskyJungs.museum.models.entities.projections.MilitaryRankProjection;
import ru.solovetskyJungs.museum.repositories.MilitaryRankRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilitaryRankService {
    private final MilitaryRankRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentsService fileAttachmentsService;

    public List<MilitaryRank> getAll() {
        return repository.findAll();
    }

    public MilitaryRank getById(Long rankId) {
        return repository.findById(rankId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(MilitaryRank militaryRank, MultipartFile image) {
        FileAttachment attachment = fileAttachmentsService.saveImage(image);
        militaryRank.setImage(attachment);
        repository.save(militaryRank);
    }

    @Transactional
    public void delete(Long id) {
        MilitaryRank militaryRankToDelete = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        fileStorageService.deleteFile(militaryRankToDelete.getImage().getUri());

        repository.delete(militaryRankToDelete);
    }

    @Transactional
    public void edit(Long id, MilitaryRank updatedRank) {
        MilitaryRank rankToUpdate = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        rankToUpdate.setTitle(updatedRank.getTitle());

        repository.save(rankToUpdate);
    }

    @Transactional
    public void changePreview(Long id, MultipartFile image) {
        MilitaryRank rankToUpdate = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        FileAttachment preview = rankToUpdate.getImage();
        if (preview != null) {
            fileAttachmentsService.delete(preview);
        }

        FileAttachment newImage = fileAttachmentsService.saveImage(image);
        rankToUpdate.setImage(newImage);

        repository.save(rankToUpdate);
    }

    public List<String> getTitles() {
        return repository.findTitles();
    }

    public List<MilitaryRankProjection> getAllWithShortSelect() {
        return repository.findAllWithShortSelect();
    }
}
