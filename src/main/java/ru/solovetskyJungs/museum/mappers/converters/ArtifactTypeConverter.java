package ru.solovetskyJungs.museum.mappers.converters;

import org.springframework.core.convert.converter.Converter;
import ru.solovetskyJungs.museum.models.enums.ArtifactType;

public class ArtifactTypeConverter implements Converter<String, ArtifactType> {
    @Override
    public ArtifactType convert(String source) {
        try {
            return ArtifactType.fromValue(Integer.parseInt(source));
        } catch (RuntimeException e) {
            return ArtifactType.UNDEFINED;
        }
    }
}
