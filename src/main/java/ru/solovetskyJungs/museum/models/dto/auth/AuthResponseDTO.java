package ru.solovetskyJungs.museum.models.dto.auth;

public record AuthResponseDTO(String token, Integer expiresIn) {
}
