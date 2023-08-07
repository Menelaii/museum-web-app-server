package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Biography extends AbstractEntity {
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthDate;
    private String placeOfBirth;
    private LocalDate dateOfDeath;
    private String placeOfDeath;

    @OneToMany(mappedBy = "biography", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<MilitaryRankDetails> militaryRankDetails;

    @OneToMany(mappedBy = "biography", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<MedalDetails> medalDetails;

    @OneToMany(mappedBy = "biography",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CareerDetails> militaryServiceDetails;

    @OneToMany(mappedBy = "biography",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CareerDetails> employmentHistory;

    @OneToMany(mappedBy = "biography",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private List<BiographyAttachment> images;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinColumn(name = "file_attachment_id", referencedColumnName = "id")
    private FileAttachment presentation;
}
