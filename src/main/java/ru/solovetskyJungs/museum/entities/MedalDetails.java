package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedalDetails extends AbstractEntity {
    private LocalDate dateOfAward;
    private String placeOfAward;

    @ManyToOne
    @JoinColumn(name = "medal_id", referencedColumnName = "id")
    private Medal medal;

    @ManyToOne
    @JoinColumn(name = "biography_id", referencedColumnName = "id")
    private Biography biography;
}
