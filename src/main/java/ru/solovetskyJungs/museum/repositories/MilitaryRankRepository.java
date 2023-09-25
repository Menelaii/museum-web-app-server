package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.MilitaryRank;
import ru.solovetskyJungs.museum.entities.projections.MilitaryRankProjection;

import java.util.List;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRank, Long> {

    @Query("SELECT r.title FROM MilitaryRank r")
    List<String> findTitles();

    @Query("SELECT r.id AS id, r.title AS title FROM MilitaryRank r")
    List<MilitaryRankProjection> findAllWithShortSelect();
}
