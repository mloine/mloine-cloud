package com.mloine.msclass.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
/**
 *  @Author: XueYongKang
 *  @Description:    由于子上线文 引发重复扫描问题 所以ribbon 和 fegin 配置文件放到扫描包意外 或者不使用配置注解
 *  @Data: 2019/12/18 15:25
 */
public class GlobalFeginClientConfiguretion {

    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.FULL;
    }
}
