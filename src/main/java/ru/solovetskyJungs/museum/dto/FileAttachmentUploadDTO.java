package ru.solovetskyJungs.museum.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileAttachmentUploadDTO(String description, MultipartFile file) {
}
