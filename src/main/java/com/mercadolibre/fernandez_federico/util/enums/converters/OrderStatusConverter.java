package com.mercadolibre.fernandez_federico.util.enums.converters;

import com.mercadolibre.fernandez_federico.util.enums.AccountType;
import com.mercadolibre.fernandez_federico.util.enums.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    /**
     * Converts a {@link OrderStatus} to the correspondig String that is used in the database
     */
    @Override
    public String convertToDatabaseColumn(OrderStatus specialCharacter) {
        return specialCharacter.toString();
    }

    /**
     * Converts a String from the database to the corresponding {@link OrderStatus}.
     */
    @Override
    public OrderStatus convertToEntityAttribute(String dbValue) {
        return OrderStatus.valueOfKey(dbValue);
    }

}