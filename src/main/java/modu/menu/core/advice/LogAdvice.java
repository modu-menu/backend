package modu.menu.core.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAdvice {

    @Pointcut("@annotation(modu.menu.core.annotation.ErrorLog)")
    public void errorLog() {
    }

    @Before("errorLog()")
    // 예외 발생시 자동으로 로그를 기록한다.
    public void errorLogAdvice(JoinPoint jp) throws Exception {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            if (arg instanceof Exception) {
                log.error("error: " + arg);
            }
        }
    }
}