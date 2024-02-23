package modu.menu.review.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import modu.menu.core.annotation.EnumValidation;
import modu.menu.vibe.domain.VibeType;

@Schema(description = "분위기 요청 DTO")
@Getter
@AllArgsConstructor
public class VibeRequest {

    @Schema(description = "분위기", example = "시끌벅적해요")
    @NotNull(message = "vibe는 필수입니다.")
    @EnumValidation(enumClass = VibeType.class, message = "분위기를 정확하게 입력해주세요.")
    private VibeType type;
}
