package com.mloine.ribbenconfiguration;

import com.mloine.msclass.ribbon.MyRibbonRule;
import com.mloine.msclass.ribbon.MyRibbonRuleV2;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRul(){
//        return new RandomRule();
//        return new MyRibbonRule();
        return new MyRibbonRuleV2();
    }

//    @Bean
//    public IPing ping(){
//        return new PingUrl();
//    }



}
