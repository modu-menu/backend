package modu.menu.core.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
// 요청과 응답을 로그에 기록한다.
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 비즈니스 로직 진입 전
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter((ServletRequest) req, (ServletResponse) resp);

        // 비즈니스 로직 진입 후
        // request
        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            String headerValue = req.getHeader(headerKey);
            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(": ")
                    .append(headerValue)
                    .append("] ");
        });
        String requestBody = req.getContentAsByteArray() + "";
        String requestURI = req.getRequestURI();
        String method = req.getMethod();
        log.info(">>>>> uri: {}, method: {}, header: {}, body: {}", requestURI, method, headerValues, requestBody);

        // response
        StringBuilder responseHeaderValues = new StringBuilder();
        resp.getHeaderNames().forEach(headerKey -> {
            String headerValue = resp.getHeader(headerKey);
            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(": ")
                    .append(headerValue)
                    .append("] ");
        });
        String responseBody = resp.getContentAsByteArray() + "";
        log.info("<<<<< uri: {}, method: {}, header: {}, body: {}", requestURI, method, responseHeaderValues, responseBody);

        // 이게 있어야 response가 비어있는 채로 전달되지 않는다.
        resp.copyBodyToResponse();
    }
}
