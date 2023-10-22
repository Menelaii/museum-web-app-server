package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.solovetskyJungs.museum.models.entities.CareerDetails;
import ru.solovetskyJungs.museum.models.enums.CareerType;

import java.util.List;

@Repository
public interface CareerDetailsRepository extends JpaRepository<CareerDetails, Long> {
    @Query("SELECT c FROM CareerDetails c WHERE c.biography.id = :biographyId AND c.careerType = :careerType")
    List<CareerDetails> getCareerDetails(Long biographyId, CareerType careerType);

    @Query("SELECT c.placeOfService FROM CareerDetails c WHERE c.careerType= :careerType")
    List<String> findPlacesOfService(CareerType careerType);
}
