package com.mloine.msclass.ribbon;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.discovery.ConsulServerUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
/**
 *  @Author: XueYongKang
 *  @Description:    解决时延
 *  @Data: 2019/12/17 15:04
 */
public class MyRibbonRuleV2 extends AbstractLoadBalancerRule {


    @Autowired
    private ConsulDiscoveryProperties consulDiscoveryProperties;

    @Autowired
    private ConsulClient consulClient;

    /**
     *
     * @param iClientConfig
     */
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
            //读取配置
    }

    /**
     *
     * 基于元数据 同机房优先调用
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        //1.获得想要调用微服务的实例列表
        ILoadBalancer lb = this.getLoadBalancer();

        ZoneAwareLoadBalancer loadBalancer = (ZoneAwareLoadBalancer) lb;
        //想要调用微服务的名称
        String name = loadBalancer.getName();

        List<String> tags = consulDiscoveryProperties.getTags();

        //筛选出机房等于JIFANG=NJ这条数据
        String jiFangTag = tags.stream()
                .filter(tag -> tag.startsWith("JIFANG"))
                .findFirst()
                .orElse(null);
        //2.筛选出机房相同的实例列表
        Response<List<HealthService>> servicesResponse = this.consulClient.getHealthServices(name, jiFangTag, true, QueryParams.DEFAULT);
        List<HealthService> services = servicesResponse.getValue();

        //当前健康的用户微服务实例
        List<HealthService> healthServices = servicesResponse.getValue();
        if(CollectionUtils.isEmpty(healthServices)){
            Response<List<HealthService>> allHealthServicesResponse = this.consulClient.getHealthServices(name, null, true, QueryParams.DEFAULT);
            services = allHealthServicesResponse.getValue();
        }

        List<ConsulServer> consulServers = services.stream().map(healthService -> new ConsulServer(healthService)).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(consulServers)){return null;}
        //3.随机返回一个实例
        return this.raddomChoose(consulServers);
    }

    private Server raddomChoose(List<ConsulServer> servers){
        int i = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(i);
    }

}
