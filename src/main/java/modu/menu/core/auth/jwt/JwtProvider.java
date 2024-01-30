package modu.menu.core.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public final int ACCESS_EXPIRATION = 1000 * 60 * 60 * 24 * 365; // 액세스 토큰 만료시간: 10분(임시 로그인만 365일)
    public final int REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 14; // 리프레쉬 토큰 만료시간: 14일
    public final String TOKEN_PREFIX = "Bearer ";
    public final String ACCESS_HEADER = "Authorization";
    public final String REFRESH_HEADER = "Refresh";
    public final String ACCESS_SECRET;
    public final String REFRESH_SECRET;

    public JwtProvider(
            @Value("${jwt.access-secret}") String ACCESS_SECRET,
            @Value("${jwt.refresh-secret}") String REFRESH_SECRET
    ) {
        this.ACCESS_SECRET = ACCESS_SECRET;
        this.REFRESH_SECRET = REFRESH_SECRET;
    }

    // 액세스 토큰 생성
    public String createAccessToken(Long id) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .sign(Algorithm.HMAC512(ACCESS_SECRET));
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(Long id) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .sign(Algorithm.HMAC512(REFRESH_SECRET));
    }

    // 액세스 토큰 검증
    public DecodedJWT verifyAccessToken(String value) {
        return JWT.require(Algorithm.HMAC512(ACCESS_SECRET))
                .build()
                .verify(value);
    }

    // 리프레쉬 토큰 검증
    public DecodedJWT verifyRefreshToken(String value) {
        return JWT.require(Algorithm.HMAC512(REFRESH_SECRET))
                .build()
                .verify(value);
    }
}
