package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
*@author:Wgy
*@date:2020/6/1 0001 22:09
*@description:
*/
@Configuration
public class ApplicationContextConfig {


    @Bean
    @LoadBalanced //如果用自定义的负载均衡方式或者更换默认轮训方式，必须注释这个，
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
