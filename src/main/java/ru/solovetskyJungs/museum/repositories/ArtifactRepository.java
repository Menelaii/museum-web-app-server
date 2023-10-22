package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.Artifact;

import java.util.Optional;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long>, JpaSpecificationExecutor<Artifact> {
    @Query("SELECT DISTINCT a FROM Artifact a LEFT JOIN FETCH a.images WHERE a.id = ?1")
    Optional<Artifact> findByIdWithImages(Long id);
}
