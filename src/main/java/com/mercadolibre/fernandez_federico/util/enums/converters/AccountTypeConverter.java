package com.mercadolibre.fernandez_federico.util.enums.converters;

import com.mercadolibre.fernandez_federico.util.enums.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    /**
     * Converts a {@link AccountType} to the correspondig String that is used in the database
     */
    @Override
    public String convertToDatabaseColumn(AccountType specialCharacter) {
        return specialCharacter.toString();
    }

    /**
     * Converts a String from the database to the corresponding {@link AccountType}.
     */
    @Override
    public AccountType convertToEntityAttribute(String dbValue) {
        return AccountType.valueOfKey(dbValue);
    }

}