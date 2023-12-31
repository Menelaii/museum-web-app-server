package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.models.entities.MilitaryRank;
import ru.solovetskyJungs.museum.models.entities.MilitaryRankDetails;
import ru.solovetskyJungs.museum.repositories.MilitaryRankDetailsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilitaryRankDetailsService {
    private final MilitaryRankDetailsRepository repository;
    private final MilitaryRankService militaryRankService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MilitaryRankDetails save(MilitaryRankDetails details) {
        MilitaryRank militaryRank = militaryRankService.getById(details.getRank().getId());

        MilitaryRankDetails entity = repository.save(details);
        entity.setRank(militaryRank);

        return entity;
    }

    @Transactional
    public List<MilitaryRankDetails> save(List<MilitaryRankDetails> details) {
        List<MilitaryRankDetails> entities = new ArrayList<>();
        details.forEach((d) -> entities.add(save(d)));

        return entities;
    }
}
