package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

    Garantia("G"),
    Repuesto("R");

    private String value;

    AccountType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static AccountType valueOfKey(String key) {
        for (AccountType specialCharacter : values()) {
            if (specialCharacter.toString().equals(key)) {
                return specialCharacter;
            }
        }

        throw new IllegalArgumentException("Illegal key");
    }
}
