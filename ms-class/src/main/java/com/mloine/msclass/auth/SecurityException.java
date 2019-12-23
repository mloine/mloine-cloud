package com.mloine.msclass.auth;
/**
 *  @Author: XueYongKang
 *  @Description:    自定义异常
 *  @Data: 2019/12/20 15:29
 */
public class SecurityException extends RuntimeException{

    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }
}
