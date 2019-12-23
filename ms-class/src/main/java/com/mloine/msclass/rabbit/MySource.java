package com.mloine.msclass.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 *  @Author: XueYongKang
 *  @Description:
 *  @Data: 2019/12/19 13:44
 */
public interface MySource {


    String MY_OUT_PUT = "myOutPut";

    @Output(MY_OUT_PUT)
    MessageChannel output();
}
