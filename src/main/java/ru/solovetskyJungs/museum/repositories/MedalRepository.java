package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.Medal;
import ru.solovetskyJungs.museum.entities.projections.MedalProjection;

import java.util.List;

@Repository
public interface MedalRepository extends JpaRepository<Medal, Long> {
    @Query("SELECT m.title FROM Medal m")
    List<String> findTitles();

    @Query("SELECT m.id AS id, m.title AS title FROM Medal m")
    List<MedalProjection> findAllWithShortSelect();
}
