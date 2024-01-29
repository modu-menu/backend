package modu.menu.core.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Schema(description = "공통 응답 DTO")
@Getter
@JsonPropertyOrder({"status", "message", "data"})
public class ApiCommonResponse<T> {
    @Schema(description = "HTTP 상태 코드", example = "200")
    private final int status;
    @Schema(description = "성공 메시지")
    private final String message;
    @Schema(description = "응답 데이터")
    private T data;

    // 요청에 성공한 경우(응답할 데이터가 없음)
    public ApiCommonResponse() {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
    }

    // 요청에 성공한 경우(응답할 데이터가 있음)
    public ApiCommonResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    // 요청에 실패한 경우
    public ApiCommonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
