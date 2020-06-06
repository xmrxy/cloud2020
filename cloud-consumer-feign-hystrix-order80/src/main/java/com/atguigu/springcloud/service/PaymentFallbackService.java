package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService  implements PaymentHystrixService {


    @Override
    public String paymentInfo_OK(Integer id) {
        return "通配的fallback方法：paymentInfo_OK";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "通配的fallback方法：paymentInfo_Timeout";
    }

}
