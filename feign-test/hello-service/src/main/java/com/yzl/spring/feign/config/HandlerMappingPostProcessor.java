package com.yzl.spring.feign.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 后置处理器，解决springMVC接口方法参数的注解无法被继承的问题。
 *
 * @author yinzuolong
 */
public class HandlerMappingPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (RequestMappingHandlerMapping.class.isAssignableFrom(ClassUtils.getUserClass(bean))) {
            RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) bean;
            Map<RequestMappingInfo, HandlerMethod> maps = mapping.getHandlerMethods();
            for (HandlerMethod handlerMethod : maps.values()) {
                MethodParameter[] parameters = handlerMethod.getMethodParameters();
                List<Method> superMethods = findInterfaceMethods(handlerMethod.getMethod());
                for (int i = 0; i < parameters.length; i++) {
                    MethodParameter parameter = parameters[i];
                    Annotation[] parameterAnnotations = parameter.getParameterAnnotations();
                    List<Annotation> superMethodParameterAnnotations = combineMethodParameterAnnotations(superMethods, parameter.getParameterIndex());
                    superMethodParameterAnnotations.addAll(0, Arrays.asList(parameterAnnotations));
                    Annotation[] newParameterAnnotations = superMethodParameterAnnotations.toArray(new Annotation[superMethodParameterAnnotations.size()]);
                    MethodParameter newParameter = new MethodParameter(parameter) {
                        /**
                         * 覆盖父类方法，返回合并接口注解的数组
                         * @return
                         */
                        @Override
                        public Annotation[] getParameterAnnotations() {
                            return newParameterAnnotations;
                        }
                    };
                    parameters[i] = newParameter;

                }
            }
        }
        return bean;
    }

    private List<Annotation> combineMethodParameterAnnotations(List<Method> methods, int paramIndex) {
        List<Annotation> annotations = new ArrayList<>();
        for (Method method : methods) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (paramIndex < parameterAnnotations.length) {
                annotations.addAll(Arrays.asList(parameterAnnotations[paramIndex]));
            }
        }
        return annotations;
    }

    /**
     * 找到方法实现的接口方法
     *
     * @param method
     * @return
     */
    private List<Method> findInterfaceMethods(Method method) {
        List<Method> methods = new ArrayList<>();
        Set<Class<?>> interfaces = ClassUtils.getAllInterfacesForClassAsSet(method.getDeclaringClass());
        for (Class<?> superInterface : interfaces) {
            Method superMethod = null;
            try {
                superMethod = superInterface.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                //忽略异常
            }
            if (superMethod != null) {
                methods.add(superMethod);
            }
        }
        return methods;
    }

}
