package com.study.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinny-xu
 */
@Slf4j
@RestController
public class OrderController {


    @GetMapping("/order")
    public String doOrder() throws InterruptedException {
        log.info("用户来下单了...");
        Thread.sleep(2000);
        return "下单成功";
    }
}
