package com.mercadolibre.fernandez_federico.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class CodeNumberSerializator extends StdSerializer<String> {

    @Getter
    @Setter
    private Integer leadingZeros;

    protected CodeNumberSerializator() {
        super(String.class);
        this.leadingZeros = 4;
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println("value " + s);
        jsonGenerator.writeString(padLeftZeros(s, this.leadingZeros));
    }

    private String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}