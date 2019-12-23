package com.mloine.msclass.configuration;

import com.mloine.ribbenconfiguration.RibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
/**
 *  @Author: XueYongKang
 *  @Description:    ribbon 全局配置
 *  @Data: 2019/12/17 14:16
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class GlobalRibbonConfiguration {
}
