package modu.menu.core.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"cause", "result"})
public class ErrorData {

    private final String cause; // 에러가 발생한 원인("userId", "password")
    private final String result; // 결과
}
