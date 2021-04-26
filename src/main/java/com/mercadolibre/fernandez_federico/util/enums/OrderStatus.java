package com.mercadolibre.fernandez_federico.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;


public enum OrderStatus {

    Procesando("Procesando"),
    Demorado("Demorado"),
    Finalizado("Finalizando"),
    Cancelado("Cancelado");

    private String value;
    private static Map mapita = new HashMap<>();


    OrderStatus (String c){
        this.value = c;
    }


    static {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            mapita.put(orderStatus.value, orderStatus);
        }
    }

    public static OrderStatus valueOfEnum(String status) {
        return (OrderStatus) mapita.get(status);
    }

    public String getValue() {
        return value;
    }


}




