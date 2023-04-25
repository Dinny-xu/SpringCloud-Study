package com.study.providera.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinny-xu
 */
@RestController
public class ProviderController {


    @GetMapping("/info")
    public String hello() {
        return "我是provider-a";
    }

}
