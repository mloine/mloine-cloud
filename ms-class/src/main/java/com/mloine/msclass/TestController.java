package com.mloine.msclass;

import com.mloine.msclass.auth.CheckAuthz;
import com.mloine.msclass.domain.dto.UserDto;
import com.mloine.msclass.rabbit.MySource;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.collection.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;


    @Autowired
    private RetryRegistry retryRegistry;

    @Autowired
    private Source source;

    @Autowired
    private MySource mySource;

    @GetMapping("/test-discovery")
    public List<ServiceInstance> testDiscovery(){
        return  discoveryClient.getInstances("ms-user");
    }

    /**
     *  @Author: XueYongKang
     *  @Description:    可视化resilience4j配置信息
     *  @Data: 2019/12/19 9:56
     */
    @GetMapping("rate-limiter-configs")
    public Seq<RateLimiter> test(){
        return this.rateLimiterRegistry.getAllRateLimiters();
    }

    @GetMapping("rate-retryr-configs")
    public Seq<Retry> test1(){
        return this.retryRegistry.getAllRetries();
    }

    @GetMapping("test-stream")
    public boolean testStream(){

         return this.source.output().send(
                MessageBuilder.withPayload("我是消息体v1")
                        .setHeader("version","v1")
                        .build()
        );
    }

    @GetMapping("test-stream-v2")
    public boolean testStreamV2(){

         return this.source.output().send(
                MessageBuilder.withPayload("我是消息体v1")
                        .setHeader("version","v2")
                        .build()
        );
    }

    @GetMapping("test-stream-my")
    public boolean testMyStream(){

         return this.mySource.output().send(
                MessageBuilder.withPayload("我是自定义接口消息体").build()
        );
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test-token-relay")
    public ResponseEntity<UserDto> testTokenRealy(@RequestHeader("Authorization") String token){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",token);

        return this.restTemplate.exchange(
                "http://ms-user/users/{id}",
                HttpMethod.GET,
                new HttpEntity<String>(httpHeaders),
                UserDto.class,
                1
        );

    }

    @GetMapping("/test-token-relay2")
    public UserDto testTokenRealy2(@RequestHeader("Authorization") String token){

        return this.restTemplate.getForObject(
                "http://ms-user/users/{id}",
                UserDto.class,
                1
        );

    }

    /**
     * 当前用户是vip的时候才允许调用
     * @param token
     * @return
     */
    @CheckAuthz(hasRole = "vip")
    @GetMapping("/vip")
    public UserDto vip(@RequestHeader("Authorization") String token){

        return this.restTemplate.getForObject(
                "http://ms-user/users/{id}",
                UserDto.class,
                1
        );

    }



}
