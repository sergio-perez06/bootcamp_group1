package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PartStatus {
    Normal("N"),
    Demorado("D");

    private String value;

    PartStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static PartStatus valueOfKey(String key) {
        for (PartStatus specialCharacter : values()) {
            if (specialCharacter.toString().equals(key)) {
                return specialCharacter;
            }
        }

        throw new IllegalArgumentException("Illegal key");
    }
}
