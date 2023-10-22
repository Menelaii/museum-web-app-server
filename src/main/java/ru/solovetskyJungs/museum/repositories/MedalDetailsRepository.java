package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.MedalDetails;

@Repository
public interface MedalDetailsRepository extends JpaRepository<MedalDetails, Long> {
}
