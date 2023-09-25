package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.MilitaryRank;
import ru.solovetskyJungs.museum.entities.projections.MilitaryRankProjection;
import ru.solovetskyJungs.museum.repositories.MilitaryRankRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilitaryRankService {
    private final MilitaryRankRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentService fileAttachmentService;

    public List<MilitaryRank> getAll() {
        return repository.findAll();
    }

    public MilitaryRank getById(Long rankId) {
        return repository.findById(rankId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(MilitaryRank militaryRank, MultipartFile image) {
        FileAttachment attachment = fileAttachmentService.saveFile(image);
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

    public List<String> getTitles() {
        return repository.findTitles();
    }

    public List<MilitaryRankProjection> getAllWithShortSelect() {
        return repository.findAllWithShortSelect();
    }
}
