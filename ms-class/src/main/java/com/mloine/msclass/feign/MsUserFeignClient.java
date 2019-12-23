package com.mloine.msclass.feign;

import com.mloine.msclass.domain.dto.UserDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


//@FeignClient(name = "ms-user",configuration = GlobalFeginClientConfiguretion.class)
@FeignClient(name = "ms-user")
//@FeignClient(url = "http://localhost:8081",name = "xxxx")  //不和ribbon使用
public interface MsUserFeignClient {

//    @RateLimiter(name = "x",fallbackMethod = "test")
    @GetMapping("/users/{usrId}")
    UserDto findUserById(@PathVariable("usrId") Integer userId);
//   @GetMapping("/users/{usrId}")
//    UserDto findUserById(@PathVariable("usrId") Integer userId, @RequestHeader("Authorization")String token);

//    default UserDto test(Integer userId, Throwable throwable){
//        return new UserDto();
//    }

//    UserDto userDto = restTemplate.getForObject(
//            "http://ms-user/users/{id}",
//            UserDto.class,
//            userId
//    );
}
