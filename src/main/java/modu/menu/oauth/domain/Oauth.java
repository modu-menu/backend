package modu.menu.oauth.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.user.domain.User;
import modu.menu.user.dto.UserResponse;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "oauth_tb")
@Entity
public class Oauth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Long oauthId; // 카카오 회원 번호
    @Enumerated(EnumType.STRING)
    private Provider provider; // 서비스 제공처(카카오, 네이버, etc)
    private String accessToken;
    private String refreshToken;
    private String ci; // 카카오 계정의 암호화된 이용자 확인 값, 기존 회원 데이터베이스에 동일한 회원이 있는지 대조하는 등 최소한의 목적으로만 참고하도록 제공되는 정보

    public void update(UserResponse.KakaoToken kakaoToken) {
        this.accessToken = kakaoToken.getAccessToken();
        this.refreshToken = kakaoToken.getRefreshToken();
    }
}
