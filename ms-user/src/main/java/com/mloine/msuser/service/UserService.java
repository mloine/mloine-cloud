package com.mloine.msuser.service;

import com.mloine.msuser.domain.dto.UserLoginDto;
import com.mloine.msuser.domain.dto.UserMoneyDto;
import com.mloine.msuser.domain.entity.User;
import com.mloine.msuser.domain.entity.UserAccountEventLog;
import com.mloine.msuser.jwt.JwtOperator;
import com.mloine.msuser.repository.UserAccountEventLogRepository;
import com.mloine.msuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountEventLogRepository userAccountEventLogRepository;

    @Autowired
    private JwtOperator jwtOperator;

    public User findById(Integer id){
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("用户不存在！"));
    }


    public void lessonBuy(UserMoneyDto moneyDto){
        //1.接受消息
        //2.扣减余额
        Integer userId = moneyDto.getUserId();
        BigDecimal money = moneyDto.getMoney();

        Optional<User> optionalUser = this.userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("当前用户ID不存在"));
        user.setMoney(user.getMoney().subtract(money));

        this.userRepository.save(user);
        //3.记录日志 到user_acount_event_log
        this.userAccountEventLogRepository.save(new UserAccountEventLog(
                userId,
                money,
                moneyDto.getEvent(),
                new Date(),
                moneyDto.getDescription()
        ));
    }

    public String login(UserLoginDto userLoginDto) {
        //1.校验帐号密码是否匹配 这里直接使用铭文  【MD5/BCript/SHA1】

        //select * from user where username = ? and  password = ?
        return  this.userRepository.findByUsernameAndPassword(userLoginDto.getUserName(), userLoginDto.getPassword()).map(user -> {
            //2.如果匹配则办法token
           HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", user.getId());
            userInfo.put("userName", user.getUsername());
            userInfo.put("role", "vip");
            return jwtOperator.generateToken(userInfo);
        }).orElseThrow(() -> new IllegalArgumentException("帐号密码不匹配"));

    }
}

