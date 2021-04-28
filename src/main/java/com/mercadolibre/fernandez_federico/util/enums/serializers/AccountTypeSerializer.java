package com.mercadolibre.fernandez_federico.util.enums.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class AccountTypeSerializer extends StdSerializer<AccountType> {

    @Getter
    @Setter
    private Integer leadingZeros;

    protected AccountTypeSerializer() {
        super(AccountType.class);
        this.leadingZeros = 4;
    }

    @Override
    public void serialize(AccountType s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(s.toString());
    }

}