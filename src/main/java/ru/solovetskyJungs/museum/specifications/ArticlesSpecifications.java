package ru.solovetskyJungs.museum.specifications;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import ru.solovetskyJungs.museum.entities.Article;

@Deprecated
public class ArticlesSpecifications {

    public static Specification<Article> withShortSelect() {
        return (root, query, criteriaBuilder) -> {
            query.multiselect(
                    root.get("id"),
                    root.get("title"),
                    root.get("publishDate"),
                    root.get("preview")
            );

            return null;
        };
    }

    //func year doesnt exist??? extract not working
    public static Specification<Article> withYear(int year) {
        return (root, query, criteriaBuilder) -> {
            Expression<Integer> yearExpression = criteriaBuilder.function(
                    "YEAR",
                    Integer.class,
                    root.get("publishDate")
            );

            return criteriaBuilder.equal(yearExpression, year);
        };
}

    //func year doesnt exist??? extract not working
    public static Specification<Article> withMonth(int month) {
        return (root, query, criteriaBuilder) -> {
            Expression<Integer> monthExpression = criteriaBuilder.function(
                    "MONTH",
                    Integer.class,
                    root.get("publishDate")
            );

            return criteriaBuilder.equal(monthExpression, month);
        };
    }
}