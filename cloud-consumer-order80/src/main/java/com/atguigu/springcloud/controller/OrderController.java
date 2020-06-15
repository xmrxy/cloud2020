package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

//@RestController
@Controller
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance instances1 = loadBalancer.instances(instances);
        URI uri = instances1.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);

    }

    @GetMapping(value = "/consumer/payment/zipkin")
    public String getPayment() {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zipkin", String.class);
    }

    //    NIO测试下载
    @GetMapping(value = "/consumer/nio")
    public void nio(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        File file = new File("C:\\Users\\Administrator\\Desktop\\git使用.docx");
        response.setContentType("MimeType");// 设置Content-Type为文件的MimeType
        response.addHeader("Content-Disposition", "attachment;filename=" + "index.docx");// 设置文件名
        response.setContentLength((int) file.length());
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        // 6x128 KB = 768KB byte buffer

        int bufferSize = 131072;
        ByteBuffer buff = ByteBuffer.allocate(786432);
        byte[] byteArr = new byte[bufferSize];
        int nRead, nGet;
        while ((nRead = fileChannel.read(buff)) != -1) {

            if (nRead == 0) {
                continue;
            }
            buff.position(0);
            buff.limit(nRead);
            while (buff.hasRemaining()) {
                nGet = Math.min(buff.remaining(), bufferSize);
                // read bytes from disk
                buff.get(byteArr, 0, nGet);
                // write bytes to output
                response.getOutputStream().write(byteArr);
            }
            buff.clear();
        }

        buff.clear();
        fileChannel.close();
        fileInputStream.close();
        System.out.println("耗时："+(System.currentTimeMillis()-start));
    }


}
