package com.mloine.msclass.controller;

import com.mloine.msclass.auth.Login;
import com.mloine.msclass.domain.entity.Lesson;
import com.mloine.msclass.service.LessionService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// thread-pool-1 corePoolSize = 10
@RestController
@RequestMapping("/lessions")
public class LessonController {


    public static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    private LessionService lessionService;

    /**
     *  @Author: XueYongKang
     *  @Description:    购买指定id的课程
     *      默认执行顺序
     *          Retry            LOWEST_PRECEDENCE-3
     *          CircuitBreaker   LOWEST_PRECEDENCE-2
     *          RateLimiter      LOWEST_PRECEDENCE-1
     *          Bulkhead         LOWEST_PRECEDENCE
     *
     *     注意：基于THREADPOOL 线程池的Bulkhead 无法传递ThreadLocal 无法使用RequestContextHolder 获取当前request
     *  @Data: 2019/12/13 18:00
     * @returnocal
     */
    // 错误次数 20次 错误率 50%
    @Login
    @GetMapping("/buy/{id}")
    //   resilience4j 仓壁模式
//    @Bulkhead(name = "findById",fallbackMethod = "findByIdFallback",type = Bulkhead.Type.THREADPOOL)
    // resilience4j 限流模式
    @RateLimiter(name = "findById",fallbackMethod = "findByIdFallback")
    // resilience4j 短路器模式
//    @CircuitBreaker(name = "findById",fallbackMethod = "findByIdFallback")
    // resilience4j 重试机制
//    @Retry(name = "findById",fallbackMethod = "findByIdFallback")
    public Lesson findById(@PathVariable Integer id, HttpServletRequest request) throws IllegalAccessException, InterruptedException {
//    public Lesson findById(@PathVariable Integer id, HttpServletRequest request,@RequestHeader("Authorization")String token) throws IllegalAccessException, InterruptedException {
        Thread.sleep(1000L);
        //1.根据id查询lession

        //2.根据lession ，id查询user_lession, 如果没有

        //3.如果user_lession == null && 用户余额大于 lession。price
        return lessionService.buyById(id,request);
    }

    public Lesson findByIdFallback( Integer id,HttpServletRequest request,Throwable throwable){
        LOGGER.info("发生fallback:",throwable);
        //从本地缓存获取 ...
        return new Lesson();
    }
}
