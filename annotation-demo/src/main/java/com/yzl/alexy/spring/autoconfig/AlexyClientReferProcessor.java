package com.yzl.alexy.spring.autoconfig;

import com.yzl.alexy.spring.annotation.AlexyClient;
import com.yzl.alexy.spring.annotation.AlexyInterface;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

public class AlexyClientReferProcessor implements BeanPostProcessor {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                AlexyClient reference = field.getAnnotation(AlexyClient.class);
                if (reference != null) {
                    Object value = refer(field.getType());
                    if (value != null) {
                        field.set(bean, value);
                    }
                }
            } catch (Exception e) {
                throw new BeanInitializationException("Failed to init remote objectprovider reference at filed " + field.getName()
                        + " in class " + bean.getClass().getName(), e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object refer(Class<?> referenceClass) {
        Map<String, ?> beans = applicationContext.getBeansOfType(referenceClass);
        for (Object bean : beans.values()) {
            AlexyInterface anno = AnnotationUtils.findAnnotation(bean.getClass(), AlexyInterface.class);
            if (bean instanceof Proxy && anno != null) {
                return bean;
            }
        }
        return null;
    }
}
