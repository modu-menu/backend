package modu.menu.core.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Jwt 관련 상수들을 관리하는 클래스
@Component
public class JwtProperties {

    public static final int ACCESS_EXPIRATION = 1000 * 60 * 60 * 24 * 365; // 액세스 토큰 만료시간: 10분(임시 로그인만 365일)
    public static final int REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 14; // 리프레쉬 토큰 만료시간: 14일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static String ACCESS_SECRET;
    public static String REFRESH_SECRET;

    @Value("${jwt.access-secret}")
    public void setAccessSecret(String accessSecret) {
        ACCESS_SECRET = accessSecret;
    }

    @Value("${jwt.access-secret}")
    public void setRefreshSecret(String refreshSecret) {
        REFRESH_SECRET = refreshSecret;
    }
}
