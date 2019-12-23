package com.mloine.msuser.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

/**
 *  @Author: XueYongKang
 *  @Description:    stream
 *  @Data: 2019/12/19 12:28
 */
@Component
public class TestStreamListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestStreamListener.class);

    /**
     *  @Author: XueYongKang
     *  @Description:    condition 支持SpEL
     *  @Data: 2019/12/19 14:14
     */
    @StreamListener(value = Sink.INPUT,condition = "headers['version']=='v1'")
    public void test(String message){
        LOGGER.info("v1版本 消费消息了,message = {}",message);
    }


    @StreamListener(value = Sink.INPUT,condition = "headers['version']=='v2'")
    public void testV2(String message){
        LOGGER.info("v2版本 消费消息了,message = {}",message);
    }

    @StreamListener(MySink.MYINPUT)
    public void test2(String message){
        LOGGER.info("自定义接口消费消费消息了,message = {}",message);
    }

    /**
     *  @Author: XueYongKang
     *  @Description:    全局处理异常
     *   message 全是需要处理的消息
     *  @Data: 2019/12/19 13:56
     */
    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        LOGGER.info("Handling ERROR: {}" ,errorMessage);
    }
}
