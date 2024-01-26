package modu.menu.core.auth.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modu.menu.core.exception.Exception401;
import modu.menu.core.response.ErrorMessage;
import modu.menu.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestMethod = req.getMethod();
        String requestUri = req.getRequestURI();

        // 토큰 인증을 필요로 하지 않는 요청들은 별도의 필터링 없이 통과시킨다.
        if (!needAuthentication(requestMethod, requestUri)) {
            log.debug("토큰 인증을 생략합니다.");
            chain.doFilter(req, resp);
            return;
        }

        String accessToken = req.getHeader(JwtProperties.HEADER_ACCESS);
        String accessTokenValue = accessToken.replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰을 복호화한 뒤 .
        try {
            DecodedJWT decodedJWT = JwtProvider.verifyAccessToken(accessTokenValue);
            Long userId = Long.parseLong(decodedJWT.getSubject());

            userRepository.findById(userId).orElseThrow(
                    () -> new Exception401(ErrorMessage.NOT_EXIST_USER_TOKEN)
            );

            // TODO 회원 탈퇴 기능 추가 시 상태 값 검증 필요

        } catch (SignatureVerificationException e) { // 토큰 검증 실패 시
            log.error(ErrorMessage.TOKEN_VERIFICATION_FAIL + "");
            throw new Exception401(ErrorMessage.TOKEN_VERIFICATION_FAIL);
        } catch (TokenExpiredException e) { // 만료된 토큰일 시
            log.error(ErrorMessage.EXPIRED_TOKEN + "");
            throw new Exception401(ErrorMessage.EXPIRED_TOKEN);
        } finally {
            chain.doFilter(req, resp);
        }
    }

    private boolean needAuthentication(String method, String uri) {
        if (method.equals("post") && uri.equals("/api/user")
                || method.equals("post") && uri.equals("/api/user/login")) {
            return false;
        }

        return true;
    }
}
