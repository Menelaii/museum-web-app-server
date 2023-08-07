package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.entities.Medal;
import ru.solovetskyJungs.museum.entities.MedalDetails;
import ru.solovetskyJungs.museum.repositories.MedalDetailsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedalDetailsService {
    private final MedalDetailsRepository repository;
    private final MedalService medalService;

    public List<MedalDetails> getAll() {
        return repository.findAll();
    }

    @Transactional
    public MedalDetails save(MedalDetails medalDetails) {
        Medal medal = medalService
                .getById(medalDetails.getMedal().getId())
                .orElseThrow(EntityNotFoundException::new);

        MedalDetails entity = repository.save(medalDetails);
        entity.setMedal(medal);

        return entity;
    }

    @Transactional
    public List<MedalDetails> save(List<MedalDetails> details) {
        List<MedalDetails> entities = new ArrayList<>();
        details.forEach((d) -> entities.add(save(d)));

        return entities;
    }

    @Transactional
    public void delete(Long id) {
        MedalDetails medalDetailsToDelete = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        repository.delete(medalDetailsToDelete);
    }
}
