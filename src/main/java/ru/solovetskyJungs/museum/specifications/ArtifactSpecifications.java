package ru.solovetskyJungs.museum.specifications;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import ru.solovetskyJungs.museum.models.entities.Artifact;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;
import ru.solovetskyJungs.museum.models.enums.ValueCategory;

public class ArtifactSpecifications {
    public static Specification<Artifact> withTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    title.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Artifact> withType(ArtifactType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Artifact> withCategory(ValueCategory category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("valueCategory"), category);
    }

    public static Specification<Artifact> withShortSelect() {
        return (root, query, criteriaBuilder) -> {
            query.multiselect(
              root.get("id"),
              root.get("title"),
              root.get("images"),
              root.get("valueCategory"),
              root.get("type")
            );

            return null;
        };
    }

    //not working with pagination
    public static Specification<Artifact> withImages() {
        return (root, query, criteriaBuilder) -> {
            root.fetch("images", JoinType.LEFT);
            query.distinct(true);

            return null;
        };
    }
}
