package modu.menu.core.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    // 액세스 토큰 생성
    public static String createAccessToken(Long id) {
        return JwtProperties.TOKEN_PREFIX + JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION))
                .sign(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET));
    }

    // 리프레쉬 토큰 생성
    public static String createRefreshToken(Long id) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION))
                .sign(Algorithm.HMAC512(JwtProperties.REFRESH_SECRET));
    }

    // 액세스 토큰 검증
    public static DecodedJWT verifyAccessToken(String value) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.ACCESS_SECRET))
                .build()
                .verify(value);
    }

    // 리프레쉬 토큰 검증
    public static DecodedJWT verifyRefreshToken(String value) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.REFRESH_SECRET))
                .build()
                .verify(value);
    }
}
