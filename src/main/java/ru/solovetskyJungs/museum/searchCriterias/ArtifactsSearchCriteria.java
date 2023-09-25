package ru.solovetskyJungs.museum.searchCriterias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.solovetskyJungs.museum.enums.ArtifactType;
import ru.solovetskyJungs.museum.enums.ValueCategory;

@Getter
@Setter
public class ArtifactsSearchCriteria {
    private String title;
    private ArtifactType artifactType;
    private ValueCategory valueCategory;
}
