package modu.menu.core.auth.jwt;

// Jwt 관련 상수들을 관리하는 클래스
public class JwtProperties {

    public static final int EXPIRATION_ACCESS = 1000 * 60 * 60 * 24 * 365; // 액세스 토큰 만료시간: 10분(임시 로그인만 365일)
    public static final int EXPIRATION_REFRESH = 1000 * 60 * 60 * 24 * 14; // 리프레쉬 토큰 만료시간: 14일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_ACCESS = "Authorization";
    public static final String HEADER_REFRESH = "Refresh";
    public static final String SECRET_ACCESS = "modu";
    public static final String SECRET_REFRESH = "menu";
}
