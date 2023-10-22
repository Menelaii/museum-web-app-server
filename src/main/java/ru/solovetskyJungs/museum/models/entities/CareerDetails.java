package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.solovetskyJungs.museum.models.enums.CareerType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CareerDetails extends AbstractEntity {
    private String placeOfService;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.ORDINAL)
    private CareerType careerType;

    @ManyToOne
    @JoinColumn(name = "biography_id", referencedColumnName = "id")
    private Biography biography;
}
