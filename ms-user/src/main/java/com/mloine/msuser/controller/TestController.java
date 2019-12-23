package com.mloine.msuser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *  @Author: XueYongKang
 *  @Description:     @RefreshScope 动态刷新配置  完成动态刷新被指文件信息 不需要重新启动服务
 *  @Data: 2019/12/16 15:47
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${first.config:mloine}")
    private String config;


    @GetMapping("/test-config")
    public String testConfig(){
        return this.config;
    }
}
