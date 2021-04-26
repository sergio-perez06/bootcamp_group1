package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PartStatus {
    Normal('N'),
    Demorado('D');

    private Character value;
    private static Map mapita = new HashMap<>();


    PartStatus (Character c){
        this.value = c;
    }


    static {
        for (PartStatus p : PartStatus.values()) {
            mapita.put(p.value, p);
        }
    }

    public static PartStatus valueOf(Character status) {
        return (PartStatus) mapita.get(status);
    }

    @JsonValue
    public Character getValue() {
        return value;
    }
}
