package com.mloine.msclass.auth;

import com.mloine.msclass.jwt.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/20 15:22
 */
@Order(1)
@Aspect
@Component
public class AuthAspect {

    @Autowired
    private JwtOperator jwtOperator;

    @Around("@annotation(com.mloine.msclass.auth.Login)")
    public Object checkLogin(ProceedingJoinPoint point)  {

        try {
            this.validateToken();

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException(throwable);
        }
    }


    @Around("@annotation(com.mloine.msclass.auth.CheckAuthz)")
    public Object CheckAuthz(ProceedingJoinPoint point)  {

        try {
            //1.校验token
            HttpServletRequest request = validateToken();

            //2.判断角色石佛ok
            Object role = request.getAttribute("role");
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            CheckAuthz checkAuthz = method.getAnnotation(CheckAuthz.class);
            String hasRole = checkAuthz.hasRole();
            if(!Objects.equals(role,hasRole)){
                throw new SecurityException("当前用户不具备"+hasRole+"的权限");
            }

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException(throwable);
        }
    }

    private HttpServletRequest validateToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){throw new SecurityException("token没有传");}
        Boolean aBoolean = jwtOperator.validateToken(token);
        if(!aBoolean){ throw new SecurityException("token非法"); }
        Claims userInfo = this.jwtOperator.getClaimsFromToken(token);
        request.setAttribute("userId",userInfo.get("userId"));
        request.setAttribute("userName",userInfo.get("userName"));
        request.setAttribute("role",userInfo.get("role"));
        return request;
    }

}
