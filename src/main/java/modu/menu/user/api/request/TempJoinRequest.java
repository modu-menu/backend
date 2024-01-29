package modu.menu.user.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.user.domain.Gender;

import java.time.LocalDate;

@Schema(description = "회원가입(임시) 요청 DTO")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TempJoinRequest {

    @Schema(description = "이메일", example = "hangang416@naver.com")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank
    private String password;

    @Schema(description = "이름")
    @NotBlank
    private String name;

    @Schema(description = "닉네임")
    @NotBlank
    private String nickname;

    @Schema(description = "성별", example = "M", allowableValues = {"M", "W"})
    @Pattern(
            regexp = "^MW$",
            message = "성별 값은 'M' 또는 'W'만 입력해주세요."
    )
    private String gender;

    @Schema(description = "연령", example = "25")
    @NotNull
    @PositiveOrZero
    private Integer age;

    @Schema(description = "생일", example = "1997-01-01")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @Schema(description = "휴대폰 번호", example = "01012345678")
    @Pattern(
            regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
            message = "유효하지 않은 휴대폰 번호 형식입니다."
    )
    private String phoneNumber;

}
