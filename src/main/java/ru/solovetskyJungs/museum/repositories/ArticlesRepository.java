package ru.solovetskyJungs.museum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.solovetskyJungs.museum.entities.Article;
import ru.solovetskyJungs.museum.entities.projections.ArticleProjection;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT a.id AS id, a.title AS title, a.publish_date AS publishDate, " +
            "f.id AS previewId, f.uri AS previewUri " +
            "FROM article a " +
            "LEFT JOIN file_attachment f ON a.file_attachment_id = f.id " +
            "WHERE (EXTRACT(YEAR FROM a.publish_date) = :year OR :year IS NULL) " +
            "AND (EXTRACT(MONTH FROM a.publish_date) = :month OR :month IS NULL) " +
            "ORDER BY a.publish_date DESC " +
            "LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<ArticleProjection> findArticlesByYearAndMonth(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset
    );

    @Query(value = "SELECT a.id AS id, a.title AS title, a.publish_date AS publishDate, " +
            "f.id AS previewId, f.uri AS previewUri FROM article a " +
            "LEFT JOIN file_attachment f ON a.file_attachment_id = f.id " +
            "ORDER BY a.publish_date DESC " +
            "LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<ArticleProjection> findLatest(
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset
    );
}
