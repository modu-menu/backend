package modu.menu.user.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import modu.menu.user.domain.Gender;

import java.time.LocalDate;

@Getter
public class TempJoinRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotNull
    @Pattern(
            regexp = "^MW$",
            message = "성별 값은 'M' 또는 'W'만 입력해주세요."
    )
    private Gender gender;
    @NotNull
    @PositiveOrZero
    private Integer age;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @NotBlank
    @Pattern(
            regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
            message = "유효하지 않은 휴대폰 번호 형식입니다."
    )
    private String phoneNumber;

}
