package modu.menu.review.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HasRoomAttributeConverter implements AttributeConverter<HasRoom, String> {
    // HasRoom.YES -> "Y", HasRoom.NO -> "N", HasRoom.UNKNOWN -> "-"
    @Override
    public String convertToDatabaseColumn(HasRoom attribute) {
        if (attribute.equals(HasRoom.YES)) {
            return "Y";
        } else if (attribute.equals(HasRoom.NO)) {
            return "N";
        }
        return "-";
    }

    // "Y" -> HasRoom.YES, "N" -> HasRoom.NO, "-" -> HasRoom.UNKNOWN
    @Override
    public HasRoom convertToEntityAttribute(String dbData) {
        if (dbData.equals("Y")) {
            return HasRoom.YES;
        } else if (dbData.equals("N")) {
            return HasRoom.NO;
        }
        return HasRoom.UNKNOWN;
    }
}
