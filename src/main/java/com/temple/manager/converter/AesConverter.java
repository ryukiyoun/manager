package com.temple.manager.converter;

import com.temple.manager.util.AesUtil;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class AesConverter implements AttributeConverter<String, String> {
    private final AesUtil aesUtil;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return aesUtil.encrypt(attribute);
        }
        catch (Exception e){
            throw new RuntimeException("Convert Error Entity To DataBase");
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aesUtil.decrypt(dbData);
        }
        catch (Exception e){
            throw new RuntimeException("Convert Error DataBase To Entity");
        }
    }
}
