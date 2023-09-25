package ru.solovetskyJungs.museum.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.solovetskyJungs.museum.dto.ArticleDTO;
import ru.solovetskyJungs.museum.dto.ArticleShortDTO;
import ru.solovetskyJungs.museum.dto.ArticleUploadDTO;
import ru.solovetskyJungs.museum.entities.Article;
import ru.solovetskyJungs.museum.entities.FileAttachment;
import ru.solovetskyJungs.museum.entities.projections.ArticleProjection;

@Component
@RequiredArgsConstructor
public class ArticlesMapper {
    private final ModelMapper modelMapper;
    private final FileAttachmentMapper fileAttachmentMapper;

    public ArticleShortDTO mapToShort(Article article) {
        return new ArticleShortDTO(
          article.getId(),
          article.getTitle(),
          article.getPublishDate(),
          fileAttachmentMapper.map(article.getPreview())
        );
    }

    public ArticleShortDTO mapToShort(ArticleProjection projection) {
        return new ArticleShortDTO(
                projection.getId(),
                projection.getTitle(),
                projection.getPublishDate(),
                fileAttachmentMapper.map(
                        new FileAttachment(
                                projection.getPreviewId(),
                                projection.getPreviewUri()
                        )
                )
        );
    }

    public Article map(ArticleProjection article) {
        return new Article(
                article.getId(),
                article.getTitle(),
                article.getPublishDate(),
                new FileAttachment(
                        article.getPreviewId(),
                        article.getPreviewUri()
                )
        );
    }

    public ArticleDTO map(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getPublishDate(),
                fileAttachmentMapper.map(article.getPreview()),
                article.getContent()
        );
    }

    public Article map(ArticleUploadDTO articleUploadDTO) {
        return modelMapper.map(articleUploadDTO, Article.class);
    }
}
