package com.yzl.spring.feign.remote;

import com.yzl.spring.feign.service.HelloService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "HELLO-SERVICE",url = "${remote.hello.objectprovider.host}")
public interface HelloClient extends HelloService {


}