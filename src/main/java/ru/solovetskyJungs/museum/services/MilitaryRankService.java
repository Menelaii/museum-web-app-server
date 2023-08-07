package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.MilitaryRank;
import ru.solovetskyJungs.museum.repositories.MilitaryRankRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void save(MilitaryRank militaryRank,
                     MultipartFile image,
                     String imageDescription) throws IOException {
        FileAttachment attachment = fileAttachmentService.saveFile(image, imageDescription);
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

    public Optional<MilitaryRank> getById(Long rankId) {
        return repository.findById(rankId);
    }
}
