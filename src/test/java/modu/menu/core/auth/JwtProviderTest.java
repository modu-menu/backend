package modu.menu.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import modu.menu.IntegrationTestSupporter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JwtProvider 테스트")
class JwtProviderTest extends IntegrationTestSupporter {

    @DisplayName("액세스 토큰이 올바르게 생성된다.")
    @Test
    void createToken() {
        // given
        Long id = 1L;

        // when
        String accessToken = jwtProvider.createAccessToken(id);

        // then
        assertThat(accessToken).isNotNull();
    }

    @DisplayName("액세스 토큰의 Payload(Subject)에 회원의 ID가 담긴다.")
    @Test
    void accessTokenHasValidUserId() {
        // given
        Long id = 1L;
        String accessTokenValue = jwtProvider.createAccessToken(id).replace(jwtProvider.TOKEN_PREFIX, "");

        // when
        DecodedJWT decodedJWT = jwtProvider.verifyAccessToken(accessTokenValue);

        // then
        assertThat(decodedJWT.getSubject()).isEqualTo(String.valueOf(id));
    }

    @DisplayName("액세스 토큰에 올바르지 않은 값이 담겨있다면 예외를 발생시킨다.")
    @Test
    void verifyAccessTokenByInvalidToken() {
        // given
        String accessTokenValue = "invalid value";

        // when
        assertThatThrownBy(() -> jwtProvider.verifyAccessToken(accessTokenValue))
                .isInstanceOf(JWTVerificationException.class);
    }

    @DisplayName("액세스 토큰에 만료된 값이 담겨있다면 예외를 발생시킨다.")
    @Test
    void verifyAccessTokenByExpiredToken() {
        // given
        Long id = 1L;
        String accessTokenValue = createExpiredAccessToken(id).replace(jwtProvider.TOKEN_PREFIX, "");

        // when
        assertThatThrownBy(() -> jwtProvider.verifyAccessToken(accessTokenValue))
                .isInstanceOf(TokenExpiredException.class);
    }

    private String createExpiredAccessToken(Long id) {
        return jwtProvider.TOKEN_PREFIX + JWT.create()
                .withSubject(String.valueOf(id))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1))
                .sign(Algorithm.HMAC512(jwtProvider.ACCESS_SECRET));
    }
}
