package modu.menu.core.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Schema(description = "에러 응답 DTO")
@Getter
@JsonPropertyOrder({"status", "message", "cause", "result"})
public class ApiFailResponse {
    @Schema(description = "HTTP 상태 코드", example = "400")
    private final Integer status;
    @Schema(description = "HTTP 상태 메시지")
    private final String reason;
    @Schema(description = "에러를 유발한 필드")
    private String cause;
    @Schema(description = "에러 메시지")
    private String message;

    // 요청에 실패한 경우(400)
    public ApiFailResponse(HttpStatus status, String cause, String message) {
        this.status = status.value();
        this.reason = status.getReasonPhrase();
        this.cause = cause;
        this.message = message;
    }

    // 요청에 실패한 경우(401, 403, 404, 500)
    public ApiFailResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.reason = status.getReasonPhrase();
        this.message = message;
    }
}
