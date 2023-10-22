package ru.solovetskyJungs.museum.searchCriterias;

import lombok.Getter;
import lombok.Setter;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

@Getter
@Setter
public class ArtifactsSearchCriteria {
    private String title;
    private ArtifactType artifactType;
    private ValueCategory valueCategory;
}
