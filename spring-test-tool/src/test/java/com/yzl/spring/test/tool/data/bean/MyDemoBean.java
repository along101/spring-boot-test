package com.yzl.spring.test.tool.data.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yutu
 * @date 2021-02-02
 */
@Data
public class MyDemoBean {

    private BaseDemo demo1;

    private BaseDemo demo2;

    private List<BaseDemo> demos;

    private Map<String, Object> demoMap;
}
