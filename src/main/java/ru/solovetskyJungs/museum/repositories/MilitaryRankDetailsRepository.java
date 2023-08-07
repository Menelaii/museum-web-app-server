package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.MilitaryRankDetails;

@Repository
public interface MilitaryRankDetailsRepository extends JpaRepository<MilitaryRankDetails, Long> {
}
