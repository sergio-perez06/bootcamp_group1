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
        jsonGenerator.writeString(Utils.padLeftZeros(s, this.leadingZeros));
    }


}