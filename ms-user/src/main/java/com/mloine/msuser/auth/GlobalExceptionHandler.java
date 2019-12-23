package com.mloine.msuser.auth;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  @Author: XueYongKang
 *  @Description:    全局自定义异常处理异常
 *  @Data: 2019/12/20 16:42
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Response> result(SecurityException exception){
        Response response = new Response(exception.getMessage(), 401);
        return new ResponseEntity<Response>(response, HttpStatus.UNAUTHORIZED);
    }


    class Response{
        private String message;

        private Integer code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Response() {
        }

        public Response(String message, Integer code) {
            this.message = message;
            this.code = code;
        }
    }
}
