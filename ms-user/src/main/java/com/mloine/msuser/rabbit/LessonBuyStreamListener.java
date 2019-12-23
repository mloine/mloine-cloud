package com.mloine.msuser.rabbit;

import com.mloine.msuser.domain.dto.UserMoneyDto;
import com.mloine.msuser.domain.entity.User;
import com.mloine.msuser.domain.entity.UserAccountEventLog;
import com.mloine.msuser.repository.UserAccountEventLogRepository;
import com.mloine.msuser.repository.UserRepository;
import com.mloine.msuser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/19 14:22
 */
@Component
public class LessonBuyStreamListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonBuyStreamListener.class);

    @Autowired
    private UserService userService;

    @StreamListener(Sink.INPUT)
    public void lessonBuy(UserMoneyDto moneyDto){
        LOGGER.info("收到扣减余额请求:{}",moneyDto);
            this.userService.lessonBuy(moneyDto);
    }
}
