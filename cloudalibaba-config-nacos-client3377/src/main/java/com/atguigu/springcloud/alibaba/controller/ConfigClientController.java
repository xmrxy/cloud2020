package com.atguigu.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RefreshScope //支持nacos的动态刷新功能
public class ConfigClientController {
    @Value("${com.atguigu.springcloud.alibaba.config.info}")
    private String configInfo;

    @GetMapping(value = "/com.atguigu.springcloud.alibaba.config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
