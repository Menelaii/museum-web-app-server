package ru.solovetskyJungs.museum.models.dto;

import java.util.List;

public record PageDTO<T>(List<T> models, long totalElements) {
}
