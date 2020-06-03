package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

//    @Resource
//    private PaymentDao paymentDao;
    @Override
    public int create(Payment payment) {
        return 1;
    }

    @Override
    public Payment getPaymentById(Long id) {
        Payment payment=new Payment();
        payment.setId(1L);
        payment.setSerial("巫巫测试");
        return payment;
    }
}
