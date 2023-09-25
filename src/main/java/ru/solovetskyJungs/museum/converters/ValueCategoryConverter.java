package ru.solovetskyJungs.museum.converters;

import org.springframework.core.convert.converter.Converter;
import ru.solovetskyJungs.museum.enums.ValueCategory;

public class ValueCategoryConverter implements Converter<String, ValueCategory> {
    @Override
    public ValueCategory convert(String source) {
        try {
            return ValueCategory.fromValue(Integer.parseInt(source));
        } catch (RuntimeException e) {
            return ValueCategory.UNDEFINED;
        }
    }
}
