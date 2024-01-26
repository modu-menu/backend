package modu.menu.core.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modu.menu.core.response.ApiResponse;
import modu.menu.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
// 후순위 필터(JwtAuthenticationFilter)의 예외를 핸들링하기 위한 필터
// 동일한 요청에 대해 한 번만 해당 필터를 거쳐가도록 OncePerRequestFilter 사용
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SignatureVerificationException e) {
            setErrorResponse(response, ErrorMessage.TOKEN_VERIFICATION_FAIL, e);
        } catch (TokenExpiredException e) {
            setErrorResponse(response, ErrorMessage.EXPIRED_TOKEN, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorMessage message, Exception e) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json; charset=utf-8");

        log.warn("401: " + e.getMessage());
        ApiResponse apiResponse = new ApiResponse(
                401,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                message.getValue()
        );

        String responseBody = objectMapper.writeValueAsString(apiResponse);

        PrintWriter printWriter = response.getWriter();
        printWriter.println(responseBody);
        printWriter.flush();
        printWriter.close();
    }
}
