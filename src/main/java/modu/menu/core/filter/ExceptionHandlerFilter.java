package modu.menu.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import modu.menu.core.exception.Exception401;
import modu.menu.core.exception.Exception500;
import modu.menu.core.response.ApiFailResponse;

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
        } catch (Exception401 e) {
            throwErrorResponse(response, e);
        } catch (Exception500 e) {
            throwErrorResponse(response, e);
        }
    }

    private void throwErrorResponse(ServletResponse response, Exception401 e) throws IOException {

        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");

        log.warn("401: " + e.getMessage(), e);
        ApiFailResponse apiSuccessResponse = new ApiFailResponse(
                e.status(),
                e.getMessage()
        );

        String responseBody = objectMapper.writeValueAsString(apiSuccessResponse);

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(responseBody);
        printWriter.flush();
        printWriter.close();
    }

    private void throwErrorResponse(ServletResponse response, Exception500 e) throws IOException {

        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");

        log.error("500: " + e.getMessage(), e);
        ApiFailResponse apiSuccessResponse = new ApiFailResponse(
                e.status(),
                e.getMessage()
        );

        String responseBody = objectMapper.writeValueAsString(apiSuccessResponse);

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(responseBody);
        printWriter.flush();
        printWriter.close();
    }
}
