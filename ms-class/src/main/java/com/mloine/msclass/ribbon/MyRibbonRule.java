package com.mloine.msclass.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.discovery.ConsulServerUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MyRibbonRule extends AbstractLoadBalancerRule {

    private static final String JIFANG = "JIFANG";

    @Autowired
    private ConsulDiscoveryProperties consulDiscoveryProperties;


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
        ILoadBalancer loadBalancer = this.getLoadBalancer();

        List<Server> servers = loadBalancer.getReachableServers();
        //2.筛选出机房相同的实例列表
        List<String> tags = consulDiscoveryProperties.getTags();
        //元数据
        Map<String, String> metadata = ConsulServerUtils.getMetadata(tags);
        List<Server> jiFangMatchServers = servers.stream().filter(server -> {
            ConsulServer consulServer = (ConsulServer) server;
            Map<String, String> targetMetadata = consulServer.getMetadata();
            return Objects.equals(metadata.get(JIFANG), targetMetadata.get(JIFANG));
        }).collect(Collectors.toList());

        //3.随机返回一个实例
        if(CollectionUtils.isEmpty(jiFangMatchServers)){
            return raddomChoose(servers);
        }
        return this.raddomChoose(jiFangMatchServers);
    }


    private Server raddomChoose(List<Server> servers){
        int i = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(i);
    }
}
