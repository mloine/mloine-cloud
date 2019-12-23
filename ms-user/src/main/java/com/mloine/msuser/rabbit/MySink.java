package com.mloine.msuser.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/19 13:37
 */
public interface MySink {

    String MYINPUT = "myInput";

    @Input(MYINPUT)
    SubscribableChannel input();
}
