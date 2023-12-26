package modu.menu.user.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderAttributeConverter implements AttributeConverter<Gender, String> {
    // Gender.MALE -> "M", Gender.FEMALE -> "F", Gender.UNKNOWN -> "-"
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if (attribute.equals(Gender.MALE)) {
            return "M";
        } else if (attribute.equals(Gender.FEMALE)) {
            return "F";
        }
        return "-";
    }

    // "M" -> Gender.MALE, "F" -> Gender.FEMALE, "-" -> Gender.UNKNOWN
    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData.equals("M")) {
            return Gender.MALE;
        } else if (dbData.equals("F")) {
            return Gender.FEMALE;
        }
        return Gender.UNKNOWN;
    }
}
