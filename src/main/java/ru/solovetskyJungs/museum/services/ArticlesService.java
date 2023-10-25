package ru.solovetskyJungs.museum.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.entities.Article;
import ru.solovetskyJungs.museum.models.entities.FileAttachment;
import ru.solovetskyJungs.museum.models.entities.projections.ArticleProjection;
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
    private final FileAttachmentsService fileAttachmentsService;
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
        FileAttachment previewAttachment = fileAttachmentsService.saveImage(preview);
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

    @Transactional
    public void edit(Long id, Article updated) {
        Article article = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        article.setTitle(updated.getTitle());
        article.setContent(updated.getContent());

        repository.save(article);
    }

    @Transactional
    public void changePreview(Long id, MultipartFile preview) {
        Article article = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        fileAttachmentsService.delete(article.getPreview());

        FileAttachment fileAttachment =
                fileAttachmentsService.saveImage(preview);

        article.setPreview(fileAttachment);

        repository.save(article);
    }
}
