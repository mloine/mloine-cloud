package com.mloine.msuser.controller;

import com.mloine.msuser.auth.Login;
import com.mloine.msuser.domain.dto.UserLoginDto;
import com.mloine.msuser.domain.entity.User;
import com.mloine.msuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/20 15:00
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Login
    @GetMapping("/users/{id}")
    public User findById(@PathVariable Integer id){return this.userService.findById(id);}

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto){
        return this.userService.login(userLoginDto);
    }
}
