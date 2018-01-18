package com.yzl.alexy.spring.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HelloResponse {

    private String message;

}
