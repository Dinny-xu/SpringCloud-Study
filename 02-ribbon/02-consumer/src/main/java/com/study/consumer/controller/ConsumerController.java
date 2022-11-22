package com.study.consumer.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * @author dinny-xu
 */
@Slf4j
@RestController
public class ConsumerController {

    private static int count = 0;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalancerClient loadBalancerClient;

    private Random rand;

    {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /**
     * 思考: 通过 http://provider/hello 能否直接调用
     * 1: 添加@LoadBalanced注解后为什么又可以调用了
     * <p>
     * 解决: 1: 拦截请求
     * 2: 截取主机名称
     * 3: 借助eureka做服务发现 list<服务列表>
     * 4: 通过负载均衡算法 拿到一个服务 ip+ port
     * 5: reConstructURL
     * 6: 发起请求
     *
     * @param serviceName
     * @return
     */
    @GetMapping("testRibbon")
    public String testRibbon(String serviceName) {
        // 正常调用需要获取服务的 ip + port 以及路径才能调用
        return restTemplate.getForObject("http://" + serviceName + "/hello", String.class);
    }

    /**
     * 核心是负载均衡
     * @param serviceName
     * @return
     */
    @GetMapping("testRibbonRule")
    public String testRibbonRule(String serviceName) {
        ServiceInstance choose = loadBalancerClient.choose(serviceName);
        log.info("{}:{}",choose.getHost(),choose.getPort());
        return choose.toString();
    }

    /**
     * 手写负载均衡算法
     *
     * @param serviceId
     * @return
     */
    @GetMapping("loadBalancingAlgorithm")
    public String loadBalancingAlgorithm(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (ArrayUtil.isEmpty(instances)) {
            return "提供服务者注册列表为空!";
        }
        ServiceInstance serviceInstance = loadBalanced(instances);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = StrUtil.format("http://{}:{}/hello?serviceName=provider", host, port);
        log.info("请求路径:{}", url);
        return restTemplate.getForObject(url, String.class);
    }

    private ServiceInstance loadBalanced(List<ServiceInstance> instances) {
        return instances.get(this.rand.nextInt(instances.size()));
    }


}
