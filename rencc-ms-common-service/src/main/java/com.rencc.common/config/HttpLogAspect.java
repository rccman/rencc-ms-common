package com.rencc.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rest 接口AOP 打印接口出参
 *
 * @author renchaochao
 * @throws
 * @date 2020/11/23 19:01
 */
@Slf4j
@Order(10)
@Aspect
@Component
public class HttpLogAspect {

    @Pointcut("execution(* com.rencc.*.controller..*(..))")
    public void printResponseLog() {
    }

    @Before("printResponseLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = joinPoint.getArgs();
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        //url
        log.info("\033[0;34m==> url:\033[0m {}",request.getRequestURL());
        //method
        log.info("\033[0;34m==> method:  {}\033[0m",request.getMethod());
        //args[]
        log.info("\033[0;34m==> args:  {}\033[0m", this.getLogStr(logArgs));
    }

    @AfterReturning(pointcut = "printResponseLog()", returning = "object")
    public void doAfterReturning(Object object) {
        if (object == null) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("\033[0;34m==> 请求结束,,URI={},,接口出参={}\033[0m",
                request.getRequestURI(), this.getLogStr(object));
    }

    private String getLogStr(Object object) {
        String resultStr = (object == null ? "" : JSON.toJSONString(object));
        if (resultStr.length() > 4000) {
            resultStr = resultStr.substring(0, 4000) + "....";
        }
        return resultStr;
    }
}