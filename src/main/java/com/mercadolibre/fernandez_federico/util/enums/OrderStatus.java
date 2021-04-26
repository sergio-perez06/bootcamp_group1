package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;


public enum OrderStatus {

    Procesando('P'),
    Demorado('D'),
    Finalizado('F'),
    Cancelado('C');

    private Character value;
    private static Map mapita = new HashMap<>();


    OrderStatus (Character c){
        this.value = c;
    }


    static {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            mapita.put(orderStatus.value, orderStatus);
        }
    }

    public static OrderStatus valueOf(Character status) {
        return (OrderStatus) mapita.get(status);
    }

    @JsonValue
    public Character getValue() {
        return value;
    }


}




