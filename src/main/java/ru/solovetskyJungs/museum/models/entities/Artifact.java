package ru.solovetskyJungs.museum.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

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
