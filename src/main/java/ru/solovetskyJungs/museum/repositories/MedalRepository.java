package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.entities.Medal;

@Repository
public interface MedalRepository extends JpaRepository<Medal, Long> {
}
