package com.study.userservice.controller;

import com.study.userservice.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dinny-xu
 */
@RestController
public class UserServiceController {

    /**
     * 接口可以做的事情:
     *     被动态代理 JDK代理(java interface  接口代理特征 $Proxy )
     */
    @Resource
    private UserOrderFeign userOrderFeign;

    @GetMapping("user/do/order")
    public String userDoOrder() {
        return userOrderFeign.doOrder();
    }
}
