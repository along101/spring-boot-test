package com.yzl.spring.feign.config;

import org.springframework.core.annotation.SynthesizingMethodParameter;

import java.lang.annotation.Annotation;

/**
 * @author yinzuolong
 */
public class NewHandlerMethodParameter extends SynthesizingMethodParameter {

    private volatile Annotation[] parameterAnnotations;
    private Annotation[] newParameterAnnotation;

    public NewHandlerMethodParameter(SynthesizingMethodParameter original, Annotation[] newParameterAnnotation) {
        super(original);
        this.newParameterAnnotation = newParameterAnnotation;
    }

    /**
     * 覆盖父类方法，返回合并接口注解的数组
     *
     * @return
     */
    @Override
    public Annotation[] getParameterAnnotations() {
        if (this.parameterAnnotations == null) {
            this.parameterAnnotations = adaptAnnotationArray(newParameterAnnotation);
        }
        return this.parameterAnnotations;
    }
}