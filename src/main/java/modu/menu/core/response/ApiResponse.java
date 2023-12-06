package modu.menu.core.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonPropertyOrder({"status", "message", "data"})
public class ApiResponse<T> {
    private final int status;
    private final String message;
    private T data;

    // 요청에 성공한 경우(응답할 데이터가 없음)
    public ApiResponse() {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
    }

    // 요청에 성공한 경우(응답할 데이터가 있음)
    public ApiResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    // 요청에 실패한 경우
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
