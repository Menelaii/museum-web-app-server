package ru.solovetskyJungs.museum.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ValueCategory {
    UNDEFINED(0),
    WORLD_CULTURAL_VALUES(1),
    ALL_RUSSIA_CULTURAL_VALUES(2),
    CULTURAL_VALUES_OF_PEOPLES_OF_RUSSIA(3);

    private final int value;

    ValueCategory(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ValueCategory fromValue(int value) {
        for (ValueCategory category : values()) {
            if (category.value == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid enum value: " + value);
    }
}
