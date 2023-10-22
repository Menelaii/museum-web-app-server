package ru.solovetskyJungs.museum.models.dto.medals;

import ru.solovetskyJungs.museum.models.dto.fileAttachmets.FileAttachmentDTO;

public record MedalDTO(Long id, String title, String description, FileAttachmentDTO image) {
}
