package com.example.demo;

import javax.persistence.*;

import java.sql.Date;
import java.time.*;


//@Converter(autoApply = true)
public class PersistenceDateConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        return Date.valueOf(attribute.toLocalDate());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return dbData.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
