package modu.menu.user.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderAttributeConverter implements AttributeConverter<Gender, String> {
    // Gender.MALE -> "M", Gender.FEMALE -> "F"
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute.equals(Gender.MALE) ? "M" : "F";
    }

    // "M" -> Gender.MALE, "F" -> Gender.FEMALE
    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return dbData.equals("M") ? Gender.MALE : Gender.FEMALE;
    }
}
