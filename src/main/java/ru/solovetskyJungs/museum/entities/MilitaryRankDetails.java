package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MilitaryRankDetails extends AbstractEntity {
    private LocalDate dateOfAssignment;

    @ManyToOne
    @JoinColumn(name = "rank_id", referencedColumnName = "id")
    private MilitaryRank rank;

    @ManyToOne
    @JoinColumn(name = "biography_id", referencedColumnName = "id")
    private Biography biography;
}
