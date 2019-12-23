package com.mloine.msgetway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 *  @Author: XueYongKang
 *  @Description:    路由谓词工厂必须由RoutePredicateFactory结尾
 *  约定：RoutePredicateFactory 结尾
 *              //1.读取配置文件的配置 并注入到 config参数中
 *              //2.判断当前时间是否满足要求
 *
 *  @Data: 2019/12/19 17:46
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeConfig.class);
    }


    @Override
    public Predicate<ServerWebExchange> apply(TimeConfig config) {
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        LocalTime now = LocalTime.now();
        return serverWebExchange -> now.isAfter(startTime) && now.isBefore(endTime);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime","endTime");
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String format = dateTimeFormatter.format(LocalTime.now());
        System.out.println(format);
    }
}
