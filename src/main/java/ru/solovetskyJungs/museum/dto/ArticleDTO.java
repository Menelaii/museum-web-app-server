package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record ArticleDTO(Long id,
                         String title,
                         LocalDate publishDate,
                         FileAttachmentDTO preview,
                         String content) {
}
