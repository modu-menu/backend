package modu.menu.core.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import modu.menu.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    // 액세스 토큰 생성
    public static String createAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_ACCESS))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_ACCESS));
    }

    // 리프레쉬 토큰 생성
    public static String createRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_REFRESH))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_REFRESH));
    }

    // 액세스 토큰 검증
    public static DecodedJWT verifyAccessToken(String value) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_ACCESS))
                .build()
                .verify(value);
    }

    // 리프레쉬 토큰 검증
    public static DecodedJWT verifyRefreshToken(String value) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_REFRESH))
                .build()
                .verify(value);
    }
}
