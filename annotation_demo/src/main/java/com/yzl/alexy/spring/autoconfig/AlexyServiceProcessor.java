package com.yzl.alexy.spring.autoconfig;

import com.yzl.alexy.spring.annotation.AlexyInterface;
import com.yzl.alexy.spring.annotation.AlexyService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liujingyu on 2017/12/19.
 */
@Configuration
public class AlexyServiceProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //bean字段上有@PrpcService注解，设置@PrpcService注解的bean
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                AlexyService prpcService = field.getAnnotation(AlexyService.class);
                if (prpcService != null) {
                    Object value = getPrpcServiceBean(field.getType());
                    if (value != null) {
                        field.set(bean, value);
                    }
                }
            } catch (Exception e) {
                throw new BeanInitializationException("Failed to init remote service reference at filed " + field.getName()
                        + " in class " + bean.getClass().getName(), e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Annotation annotation = AnnotationUtils.findAnnotation(bean.getClass(), AlexyService.class);
        if (annotation == null) {
            return bean;
        }
        //TODO 如果是aop代理，需要找到实现类
        List<Class<?>> interfaceClasses = findAllPrpcInterfaces(bean);
        if (interfaceClasses.size() == 0) {
            throw new RuntimeException(String.format("Can not find %s serviceImpl's interface.", bean.getClass().getName()));
        }
        for (Class<?> interfaceClass : interfaceClasses) {
            registryPrpcService(interfaceClass, bean);
        }
        return bean;
    }


    private List<Class<?>> findAllPrpcInterfaces(Object bean) {
        List<Class<?>> prpcInterfaces = new ArrayList<>();
        Set<Class<?>> interfaceClasses = ClassUtils.getAllInterfacesAsSet(bean);
        for (Class<?> interfaceClass : interfaceClasses) {
            Annotation annotation = AnnotationUtils.findAnnotation(interfaceClass, AlexyInterface.class);
            if (annotation != null) {
                prpcInterfaces.add(interfaceClass);
            }
        }
        return prpcInterfaces;
    }

    private void registryPrpcService(Class interfaceClass, Object bean) {
        //TODO 实现业务逻辑
        System.out.println(interfaceClass + "=>" + bean.getClass());
    }

    private Object getPrpcServiceBean(Class<?> prpcServiceClass) {
        Map<String, ?> beans = applicationContext.getBeansOfType(prpcServiceClass);
        for (Object bean : beans.values()) {
            AlexyService anno = AnnotationUtils.findAnnotation(bean.getClass(), AlexyService.class);
            if (anno != null) {
                return bean;
            }
        }
        return null;
    }
}
