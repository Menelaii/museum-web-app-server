package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.Biography;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Long>, JpaSpecificationExecutor<Biography> {
}
