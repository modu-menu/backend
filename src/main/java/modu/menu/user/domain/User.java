package modu.menu.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import modu.menu.BaseTime;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String ci; // 필요한지 검토 필요
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private Gender gender; // 남성의 경우 "M", 여성의 경우 "F"
    @NotBlank
    private Integer age;
    private LocalDate birthday;
    @NotBlank
    private String phoneNumber;
    private String profileImageUrl;
}
