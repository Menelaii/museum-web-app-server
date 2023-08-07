package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.Artifact;

import java.util.List;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    @Query("SELECT DISTINCT a FROM Artifact a LEFT JOIN FETCH a.images")
    List<Artifact> findAllWithImages();
}
