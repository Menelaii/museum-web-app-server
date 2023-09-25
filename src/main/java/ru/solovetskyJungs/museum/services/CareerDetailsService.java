package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solovetskyJungs.museum.entities.Biography;
import ru.solovetskyJungs.museum.entities.CareerDetails;
import ru.solovetskyJungs.museum.enums.CareerType;
import ru.solovetskyJungs.museum.repositories.CareerDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerDetailsService {
    private final CareerDetailsRepository repository;
    private final BiographiesService biographiesService;

    @Transactional
    public CareerDetails createCareerDetails(CareerDetails careerDetails) {
        Biography biography = biographiesService
                .getById(careerDetails.getBiography().getId());

        careerDetails.setBiography(biography);

        return repository.save(careerDetails);
    }

    @Transactional
    public void deleteCareerDetails(Long id) {
        repository.deleteById(id);
    }

    public List<CareerDetails> getAll() {
        return repository.findAll();
    }

    public Optional<CareerDetails> getById(Long id) {
        return repository.findById(id);
    }

    public List<String> getPlacesOfService() {
        return repository.findPlacesOfService(CareerType.MILITARY_SERVICE);
    }
}
