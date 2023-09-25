package ru.solovetskyJungs.museum.searchCriterias;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BiographySearchCriteria {
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String placeOfBirth;
    private String placeOfDeath;
    private String militaryRank;
    private String medal;
    private String placeOfService;
    private String placeOfEmployment;
}
