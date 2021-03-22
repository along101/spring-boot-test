package com.yzl.spring.feign.remote;

import com.yzl.spring.feign.service.HelloService1;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "test1", url = "${remote.hello.service.host}")
public interface HelloClient1 extends HelloService1 {


}