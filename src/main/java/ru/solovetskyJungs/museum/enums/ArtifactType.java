package ru.solovetskyJungs.museum.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ArtifactType {
    UNDEFINED(0),
    PAINTING(1),
    GRAPHICS(2),
    SCULPTURE(3),
    APPLIED_ART(4),
    NUMISMATICS(5),
    ARCHAEOLOGY(6),
    RARE_BOOKS(7),
    WEAPON(8),
    DOCUMENTS(9),
    PHOTOGRAPHS(10),
    NATURAL_SCIENCE_COLLECTION(11),
    MINERAL_COLLECTION(12),
    TECHNOLOGY(13),
    PRINTED_PRODUCTS(14),
    OTHER(15);

    private final int value;

    ArtifactType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ArtifactType fromValue(int value) {
        for (ArtifactType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid enum value: " + value);
    }
}
