package com.yzl.alexy.spring.client;

import com.yzl.alexy.spring.annotation.EnableAlexyClients;
import com.yzl.alexy.spring.demo.Simple;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAlexyClients(basePackageClasses = {Simple.class})
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
