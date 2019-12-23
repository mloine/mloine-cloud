package com.mloine.msclass;

import com.mloine.msclass.rabbit.MySource;
import com.mloine.msclass.resttemplte.TokenRelayRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@EnableBinding({Source.class, MySource.class})
@EnableFeignClients//(defaultConfiguration = GlobalFeginClientConfiguretion.class)
@SpringBootApplication
public class MsClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClassApplication.class, args);
    }

    /**
     *  @Author: XueYongKang
     *  @Description:    spring web 提供的轻量级 http client
     *               @LoadBalanced 使用客户端负载均很  ribben
     *
     *  @Data: 2019/12/13 18:18
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new TokenRelayRequestInterceptor()));
        return restTemplate;
    }

}
