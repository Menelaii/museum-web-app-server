package ru.solovetskyJungs.museum.dto;

import java.time.LocalDate;

public record ArticleShortDTO(Long id, String title, LocalDate publishDate, FileAttachmentDTO preview) {
}
