package modu.menu.core.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
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
public class ExceptionHandlerFilter implements Filter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (SignatureVerificationException e) {
            setErrorResponse(response, ErrorMessage.TOKEN_VERIFICATION_FAIL, e);
        } catch (TokenExpiredException e) {
            setErrorResponse(response, ErrorMessage.EXPIRED_TOKEN, e);
        }
    }

    private void setErrorResponse(ServletResponse response, ErrorMessage message, Exception e) throws IOException {

        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setStatus(401);
        resp.setContentType("application/json; charset=utf-8");

        log.warn("401: " + e.getMessage());
        ApiResponse apiResponse = new ApiResponse(
                401,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                message.getValue()
        );

        String responseBody = objectMapper.writeValueAsString(apiResponse);

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(responseBody);
        printWriter.flush();
        printWriter.close();
    }
}
