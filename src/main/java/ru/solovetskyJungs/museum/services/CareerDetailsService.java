package ru.solovetskyJungs.museum.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.models.entities.CareerDetails;
import ru.solovetskyJungs.museum.models.enums.CareerType;
import ru.solovetskyJungs.museum.repositories.CareerDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerDetailsService {
    private final CareerDetailsRepository repository;

    public Optional<CareerDetails> getById(Long id) {
        return repository.findById(id);
    }

    public List<String> getPlacesOfService() {
        return repository.findPlacesOfService(CareerType.MILITARY_SERVICE);
    }
}
