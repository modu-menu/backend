package modu.menu.user.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "User_tb")
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String nickname;
    private Gender gender; // 남성의 경우 "M", 여성의 경우 "F", 모르는 경우 "-"
    private Integer age;
    private LocalDate birthday;
    private String phoneNumber;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
