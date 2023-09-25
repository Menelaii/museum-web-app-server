package ru.solovetskyJungs.museum.dto;

import java.util.List;

public record PageDTO<T>(List<T> models, long totalElements) {
}
