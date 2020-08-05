package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        return "----testA";
    }

    @GetMapping("/testB")
    public String testB() throws InterruptedException {
        Thread.sleep(300);
        return "----testB";
    }

    @GetMapping("/testD")
    public String testD() {
        try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        log.info("testD 测试RT");
        return "----testD";
    }

    @GetMapping("/testE")
    @SentinelResource(value = "testE",blockHandler = "deal_testHotKey" ,fallback ="deal_forback" )
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "----testE 测试异常数";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2) {
        return "----testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "----deal_testHotKey, o(╥﹏╥)o"; // sentinel的默认提示都是： Blocked by Sentinel (flow limiting)
    }
    public String deal_forback() {
        return "----deal_forback, o(╥﹏╥)o"; // sentinel的默认提示都是： Blocked by Sentinel (flow limiting)
    }
}