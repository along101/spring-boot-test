package com.yzl.spring.feign.remote;

import com.yzl.spring.feign.service.HelloService2;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "HELLO-SERVICE")
public interface HelloClient2 extends HelloService2 {


}