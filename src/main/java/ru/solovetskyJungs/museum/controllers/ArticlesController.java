package ru.solovetskyJungs.museum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.solovetskyJungs.museum.models.dto.articles.ArticleDTO;
import ru.solovetskyJungs.museum.models.dto.articles.ArticleShortDTO;
import ru.solovetskyJungs.museum.models.dto.articles.ArticleUploadDTO;
import ru.solovetskyJungs.museum.models.dto.PageDTO;
import ru.solovetskyJungs.museum.models.entities.Article;
import ru.solovetskyJungs.museum.mappers.ArticlesMapper;
import ru.solovetskyJungs.museum.searchCriterias.ArticlesSearchCriteria;
import ru.solovetskyJungs.museum.searchCriterias.XPage;
import ru.solovetskyJungs.museum.services.ArticlesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticlesController {
    private final ArticlesService service;
    private final ArticlesMapper articlesMapper;

    @GetMapping
    public ResponseEntity<PageDTO<ArticleShortDTO>> get(XPage page, ArticlesSearchCriteria searchCriteria) {
        List<ArticleShortDTO> dtosPage = service.findAllWithFilters(page, searchCriteria)
                .stream()
                .map(articlesMapper::mapToShort)
                .toList();


        return ResponseEntity.ok(new PageDTO<>(
                dtosPage,
                dtosPage.size()
        ));
    }

    @GetMapping("/latest")
    public ResponseEntity<PageDTO<ArticleShortDTO>> getLatest(XPage page) {
        List<ArticleShortDTO> dtosPage = service.findLatest(page)
                .stream()
                .map(articlesMapper::mapToShort)
                .toList();

        return ResponseEntity.ok(new PageDTO<>(
                dtosPage,
                dtosPage.size()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Article article = service.getById(id);

        return ResponseEntity.ok(articlesMapper.map(article));
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> createArtifact(
            @RequestPart("article") ArticleUploadDTO articleUploadDTO,
            @RequestPart("preview") MultipartFile preview) {

        Article article = articlesMapper.map(articleUploadDTO);
        service.create(article, preview);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> edit(@PathVariable Long id, @RequestBody ArticleUploadDTO uploadDTO) {
        Article article = articlesMapper.map(uploadDTO);
        service.edit(id, article);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/preview", consumes = "multipart/form-data")
    public ResponseEntity<Void> changePreview(@PathVariable Long id,
                                              @RequestPart("image") MultipartFile preview
    ) {
        service.changePreview(id, preview);
        return ResponseEntity.ok().build();
    }
}
