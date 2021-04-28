package com.mercadolibre.fernandez_federico.util.enums.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class PartStatusSerializer  extends StdSerializer<PartStatus> {

    @Getter
    @Setter
    private Integer leadingZeros;

    protected PartStatusSerializer() {
        super(PartStatus.class);
        this.leadingZeros = 4;
    }

    @Override
    public void serialize(PartStatus s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(s.toString());
    }

}