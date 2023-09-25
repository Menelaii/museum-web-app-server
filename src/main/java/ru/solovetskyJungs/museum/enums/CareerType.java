package ru.solovetskyJungs.museum.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CareerType {
    MILITARY_SERVICE(0),
    EMPLOYMENT_HISTORY(1);

    private final int value;

    CareerType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static CareerType fromValue(int value) {
        for (CareerType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid enum value: " + value);
    }
}
