package modu.menu.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(
        classes = GenderAttributeConverter.class
)
class GenderAttributeConverterTest {

    @Autowired
    private GenderAttributeConverter converter;

    @DisplayName("Gender enum을 종류에 맞게 정해진 String으로 변환한다.")
    @Test
    void convertToDatabaseColumn() {
        Gender maleGender = Gender.MALE;
        Gender femaleGender = Gender.FEMALE;

        String maleDbValue = converter.convertToDatabaseColumn(maleGender);
        String femaleDbValue = converter.convertToDatabaseColumn(femaleGender);

        assertEquals("M", maleDbValue);
        assertEquals("F", femaleDbValue);
    }

    @DisplayName("정해진 String을 알맞은 Gender enum으로 변환한다.")
    @Test
    void convertToEntityAttribute() {
        String maleDbValue = "M";
        String femaleDbValue = "F";

        Gender maleGender = converter.convertToEntityAttribute(maleDbValue);
        Gender femaleGender = converter.convertToEntityAttribute(femaleDbValue);

        assertEquals(Gender.MALE, maleGender);
        assertEquals(Gender.FEMALE, femaleGender);
    }
}