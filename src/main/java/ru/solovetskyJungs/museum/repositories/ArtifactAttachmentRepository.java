package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.ArtifactAttachment;

@Repository
public interface ArtifactAttachmentRepository extends JpaRepository<ArtifactAttachment, Long> {

    @Modifying
    @Query("Update ArtifactAttachment a" +
            " SET a.isPreview = true" +
            " WHERE a.id = :id")
    void setAsPreview(@Param("id") Long id);

    @Modifying
    @Query("Update ArtifactAttachment a" +
            " SET a.isPreview = false" +
            " WHERE a.id = :id AND a.isPreview = true")
    void unsetAsPreview(@Param("id") Long id);
}
