package modu.menu.vote.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "투표 결과 조회 요청 DTO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteResultRequest {

    @Schema(description = "회원의 현재 위도(소수점 6번째까지 포함, 정확도 10cm)", example = "126.977966")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{6})$", message = "소수점 여섯 번째자리까지 표시해서 요청해주세요.")
    @NotNull
    private Double latitude;

    @Schema(description = "회원의 현재 경도(소수점 6번째까지 표현, 정확도 10cm)", example = "37.566535")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{6})$", message = "소수점 여섯 번째자리까지 표시해서 요청해주세요.")
    @NotNull
    private Double longitude;
}
