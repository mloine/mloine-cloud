package com.mloine.msuser.auth;

import com.mloine.msuser.jwt.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/20 15:22
 */
@Aspect
@Component
public class AuthAspect {

    @Autowired
    private JwtOperator jwtOperator; 

    @Around("@annotation(com.mloine.msuser.auth.Login)")
    public Object checkLogin(ProceedingJoinPoint point)  {

        try {
            //1.获取http请求中的header(token)  静态方法拿到request
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization");
            if(StringUtils.isEmpty(token)){throw new SecurityException("token没有传");}
            //2.校验token是否合法 如果合法认为用户已经登录 不合法 返回401
            Boolean aBoolean = jwtOperator.validateToken(token);

            if(!aBoolean){ throw new SecurityException("token非法"); }
            Claims userInfo = this.jwtOperator.getClaimsFromToken(token);
            request.setAttribute("userId",userInfo.get("userId"));
            request.setAttribute("userName",userInfo.get("userName"));

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException(throwable);
        }
    }

}
