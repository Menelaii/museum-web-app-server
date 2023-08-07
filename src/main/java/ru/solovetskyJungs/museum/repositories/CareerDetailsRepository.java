package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.CareerDetails;

@Repository
public interface CareerDetailsRepository extends JpaRepository<CareerDetails, Long> {
}
