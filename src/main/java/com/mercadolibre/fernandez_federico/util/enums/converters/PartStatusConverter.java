package com.mercadolibre.fernandez_federico.util.enums.converters;

import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;
import com.mercadolibre.fernandez_federico.util.enums.PartStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PartStatusConverter implements AttributeConverter<PartStatus, String> {

    /**
     * Converts a {@link PartStatus} to the correspondig String that is used in the database
     */
    @Override
    public String convertToDatabaseColumn(PartStatus specialCharacter) {
        return specialCharacter.toString();
    }

    /**
     * Converts a String from the database to the corresponding {@link PartStatus}.
     */
    @Override
    public PartStatus convertToEntityAttribute(String dbValue) {
        return PartStatus.valueOfKey(dbValue);
    }

}