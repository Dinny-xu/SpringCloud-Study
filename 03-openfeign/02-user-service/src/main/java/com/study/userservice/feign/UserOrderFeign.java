package com.study.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @FeignClient 声明是feign调用
 * value = "order-service"  值必须与服务提供者名称一致
 * @author dinny-xu
 */
@FeignClient("order-service")
public interface UserOrderFeign {

    /**
     * 调用订单服务
     * 路径必须与服务提供者的路径一致
     * @return string
     */
    @GetMapping("/order")
    String doOrder();

}
