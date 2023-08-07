package ru.solovetskyJungs.museum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Artifact extends AbstractEntity {
    private String title;
    private LocalDate creationPeriod;

    @Enumerated(EnumType.ORDINAL)
    private ArtifactType type;

    @Enumerated(EnumType.ORDINAL)
    private ValueCategory valueCategory;


    @OneToMany(mappedBy = "artifact",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private List<ArtifactAttachment> images;
}
