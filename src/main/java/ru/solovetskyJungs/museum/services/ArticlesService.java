package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.dto.ArticleShortDTO;
import ru.solovetskyJungs.museum.entities.Article;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.projections.ArticleProjection;
import ru.solovetskyJungs.museum.mappers.ArticlesMapper;
import ru.solovetskyJungs.museum.repositories.ArticlesRepository;
import ru.solovetskyJungs.museum.searchCriterias.ArticlesSearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticlesService {
    private final ArticlesRepository repository;
    private final FileStorageService fileStorageService;
    private final FileAttachmentService fileAttachmentService;
    private final ArticlesMapper articlesMapper;

    public List<Article> findAllWithFilters(XPage page, ArticlesSearchCriteria searchCriteria) {
        List<ArticleProjection> projections = repository.findArticlesByYearAndMonth(
                searchCriteria.getYear(),
                searchCriteria.getMonth(),
                page.getItemsPerPage(),
                pageToOffset(page.getPage(), page.getItemsPerPage())
        );

        return projections
                .stream()
                .map(articlesMapper::map)
                .toList();
    }

    public List<ArticleProjection> findLatest(XPage page) {
        return repository.findLatest(
                page.getItemsPerPage(),
                pageToOffset(page.getPage(), page.getItemsPerPage())
        );
    }

    public Article getById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Article article, MultipartFile preview) {
        FileAttachment previewAttachment = fileAttachmentService.saveFile(preview);
        article.setPreview(previewAttachment);

        article.setPublishDate(LocalDate.now());

        repository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        Article articleToDelete = repository.findById(id)
                        .orElseThrow(EntityNotFoundException::new);

        fileStorageService.deleteFile(articleToDelete.getPreview().getUri());
        repository.delete(articleToDelete);
    }

    private Integer pageToOffset(Integer page, Integer itemsPerPage) {
        if (page == null) {
            return 0;
        }

        return page * itemsPerPage;
    }
}
