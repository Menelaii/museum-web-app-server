package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.Biography;

import java.util.List;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Long> {
}
