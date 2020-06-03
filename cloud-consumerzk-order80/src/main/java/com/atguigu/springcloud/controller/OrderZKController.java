package com.atguigu.springcloud.controller;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderZKController {


    @Resource
    private RestTemplate restTemplate;

    public static  final String INVOKE_URL="http://cloud-provider-payment";

    @RequestMapping(value = "/consumer/payment/zk")
    public String paymentInfo(){
        String result=restTemplate.getForObject(INVOKE_URL+"/payment/zk",String.class);
        return result;
    }
}
