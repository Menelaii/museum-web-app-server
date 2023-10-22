package ru.solovetskyJungs.museum.models.dto.militaryRanks;

import ru.solovetskyJungs.museum.models.dto.fileAttachmets.FileAttachmentDTO;

public record MilitaryRankDTO(Long id, String title, FileAttachmentDTO image) {
}
