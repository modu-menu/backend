package modu.menu.core.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Schema(description = "성공 응답 DTO")
@Getter
@JsonPropertyOrder({"status", "message", "data"})
public class ApiSuccessResponse<T> {
    @Schema(description = "HTTP 상태 코드", example = "200")
    private final Integer status;
    @Schema(description = "HTTP 상태 메시지")
    private final String reason;
    @Schema(description = "응답 데이터")
    private T data;

    // 요청에 성공한 경우(응답할 데이터가 없음)
    public ApiSuccessResponse() {
        this.status = HttpStatus.OK.value();
        this.reason = HttpStatus.OK.getReasonPhrase();
    }

    // 요청에 성공한 경우(응답할 데이터가 있음)
    public ApiSuccessResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.reason = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }
}
