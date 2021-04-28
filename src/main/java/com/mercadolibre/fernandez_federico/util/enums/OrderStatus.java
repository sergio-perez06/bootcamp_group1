package com.mercadolibre.fernandez_federico.util.enums;

public enum OrderStatus {

    Procesando("P"),
    Demorado("D"),
    Finalizado("F"),
    Cancelado("C");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static OrderStatus valueOfKey(String key) {
        for (OrderStatus specialCharacter : values()) {
            if (specialCharacter.toString().equals(key)) {
                return specialCharacter;
            }
        }

        throw new IllegalArgumentException("Illegal key");
    }

}




