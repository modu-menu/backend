package modu.menu.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import modu.menu.core.auth.jwt.JwtAuthenticationFilter;
import modu.menu.core.filter.ExceptionHandlerFilter;
import modu.menu.core.filter.LoggerFilter;
import modu.menu.user.repository.UserRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterRegisterConfig {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<?> loggerFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoggerFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<?> exceptionHandlerFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new ExceptionHandlerFilter(objectMapper));
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<?> jwtAuthenticationFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new JwtAuthenticationFilter(userRepository));
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
