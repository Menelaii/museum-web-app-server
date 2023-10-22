package ru.solovetskyJungs.museum.models.dto.articles;

import ru.solovetskyJungs.museum.models.dto.fileAttachmets.FileAttachmentDTO;

import java.time.LocalDate;

public record ArticleShortDTO(Long id, String title, LocalDate publishDate, FileAttachmentDTO preview) {
}
