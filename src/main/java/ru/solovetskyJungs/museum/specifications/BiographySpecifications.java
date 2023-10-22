package ru.solovetskyJungs.museum.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.solovetskyJungs.museum.models.entities.*;
import ru.solovetskyJungs.museum.models.enums.CareerType;

import java.time.LocalDate;

public class BiographySpecifications {
    public static Specification<Biography> withSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("surname")),
                        surname.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Biography> withName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        name.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Biography> withPatronymic(String patronymic) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("patronymic")),
                        patronymic.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Biography> withDateOfBirth(LocalDate dateOfBirth) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("birthDate"), dateOfBirth);
    }

    public static Specification<Biography> withDateOfDeath(LocalDate dateOfDeath) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dateOfDeath"), dateOfDeath);
    }

    public static Specification<Biography> withPlaceOfBirth(String placeOfBirth) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("placeOfBirth")),
                        placeOfBirth.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Biography> withPlaceOfDeath(String placeOfDeath) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("placeOfDeath")),
                        placeOfDeath.trim().toLowerCase() + "%"
                );
    }

    public static Specification<Biography> withMedal(String medalTitle) {
        return (root, query, criteriaBuilder) -> {
            Join<Biography, MedalDetails> medalDetailsJoin = root.join("medalDetails", JoinType.INNER);
            Join<MedalDetails, Medal> medalJoin = medalDetailsJoin.join("medal", JoinType.INNER);

            return criteriaBuilder.like(
                    criteriaBuilder.lower(medalJoin.get("title")),
                    medalTitle.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Biography> withMilitaryRank(String militaryRankTitle) {
        return (root, query, criteriaBuilder) -> {
            Join<Biography, MilitaryRankDetails> militaryRankDetailsJoin =
                    root.join("militaryRankDetails", JoinType.INNER);
            Join<MilitaryRankDetails, MilitaryRank> rankJoin =
                    militaryRankDetailsJoin.join("rank", JoinType.INNER);

            return criteriaBuilder.like(
                    criteriaBuilder.lower(rankJoin.get("title")),
                    militaryRankTitle.trim().toLowerCase() + "%"
            );
        };
    }

    public static Specification<Biography> withPlaceOfMilitaryService(String placeOfService) {
        return (root, query, criteriaBuilder) -> {
            Join<Biography, CareerDetails> militaryServiceJoin =
                    root.join("militaryServiceDetails", JoinType.INNER);

            Predicate placeOfServicePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(militaryServiceJoin.get("placeOfService")),
                    placeOfService.trim().toLowerCase() + "%"
            );

            Predicate careerTypePredicate = criteriaBuilder.equal(
                    militaryServiceJoin.get("careerType"),
                    CareerType.MILITARY_SERVICE
            );

            return criteriaBuilder.and(placeOfServicePredicate, careerTypePredicate);
        };
    }

    public static Specification<Biography> withPlaceOfEmployment(String placeOfEmployment) {
        return (root, query, criteriaBuilder) -> {
            Join<Biography, CareerDetails> employmentHistoryJoin =
                    root.join("employmentHistory", JoinType.INNER);

            Predicate placeOfEmploymentPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(employmentHistoryJoin.get("placeOfService")),
                    placeOfEmployment.trim().toLowerCase() + "%"
            );

            Predicate careerTypePredicate = criteriaBuilder.equal(
                    employmentHistoryJoin.get("careerType"),
                    CareerType.EMPLOYMENT_HISTORY
            );

            return criteriaBuilder.and(placeOfEmploymentPredicate, careerTypePredicate);
        };
    }

    public static Specification<Biography> withShortSelect() {
        return (root, query, criteriaBuilder) -> {
            query.multiselect(
                    root.get("id"),
                    root.get("surname"),
                    root.get("name"),
                    root.get("patronymic"),
                    root.get("birthDate"),
                    root.get("dateOfDeath")
            );

            return null;
        };
    }
}
