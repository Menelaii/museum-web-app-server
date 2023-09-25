package ru.solovetskyJungs.museum.entities.projections;

import java.time.LocalDate;

public interface ArticleProjection {
    Long getId();
    String getTitle();
    LocalDate getPublishDate();
    Long getPreviewId();
    String getPreviewUri();
}
