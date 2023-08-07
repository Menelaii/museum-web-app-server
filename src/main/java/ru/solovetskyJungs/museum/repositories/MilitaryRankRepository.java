package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.MilitaryRank;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRank, Long> {
}
