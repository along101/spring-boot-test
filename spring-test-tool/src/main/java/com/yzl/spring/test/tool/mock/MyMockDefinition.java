package com.yzl.spring.test.tool.mock;

import lombok.Data;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yutu
 * @date 2021-01-30
 */
@Data
public class MyMockDefinition {

    private String beanName = "";

    private String beanClass = "";

    private String factoryClass = "";

    private List<String> annotations = new ArrayList<>();

    private LinkedMultiValueMap<String, AnnotationAttributes> attributesMap = new LinkedMultiValueMap<>(4);
}
