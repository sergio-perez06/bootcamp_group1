package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

    Garantia('G'),
    Repuesto('R');

    private Character value;
    private static Map mapita = new HashMap<>();


    AccountType (Character c){
        this.value = c;
    }

    static {
        for (AccountType a : AccountType.values()) {
            mapita.put(a.value, a);
        }
    }

    public static AccountType valueOf(Character status) {
        return (AccountType) mapita.get(status);
    }

    @JsonValue
    public Character getValue() {
        return value;
    }




}
