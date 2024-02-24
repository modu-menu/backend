package modu.menu.core.util;

import modu.menu.core.exception.Exception500;
import modu.menu.core.response.ErrorMessage;

import java.util.Arrays;

public class EnumConverter {

    // 문자열을 원하는 Enum으로 변환
    public static <T extends Enum<T>> T stringToEnum(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new Exception500(ErrorMessage.CANT_CONVERT_STRING_TO_ENUM));
    }
}
