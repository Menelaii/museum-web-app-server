package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.BiographyAttachment;

@Repository
public interface BiographyAttachmentRepository extends JpaRepository<BiographyAttachment, Long> {

    @Modifying
    @Query("Update BiographyAttachment b" +
            " SET b.isPreview = true" +
            " WHERE b.id = :id")
    void setAsPreview(@Param("id") Long id);

    @Modifying
    @Query("Update BiographyAttachment b" +
            " SET b.isPreview = false" +
            " WHERE b.id = :id AND b.isPreview = true")
    void unsetAsPreview(@Param("id") Long id);
}
