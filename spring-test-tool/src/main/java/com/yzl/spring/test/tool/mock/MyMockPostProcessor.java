package com.yzl.spring.test.tool.mock;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.yzl.spring.test.tool.mock.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 根据注解找到需要mock的bean
 *
 * @author yutu
 * @date 2021-01-30
 */
@Slf4j
public class MyMockPostProcessor implements BeanFactoryPostProcessor, BeanClassLoaderAware, Ordered {

    private static ExpressRunner expressRunner = new ExpressRunner();

    private ClassLoader classLoader;

    @Getter
    private Map<String, MyMockDefinition> mockBeans = new ConcurrentHashMap<>();

    @Getter
    private Map<String, MyMockDefinition> allBeans = new LinkedHashMap<>();

    @Setter
    @Getter
    private Set<MyMockRule> myMocks;

    @Setter
    @Getter
    private Set<MyMockBean> myMockBeans;

    @Setter
    @Getter
    private Set<MyMockExclude> myMockExcludes;

    public static BeanDefinition getOrRegistryBeanDefinition(BeanDefinitionRegistry registry) {
        String beanName = MyMockPostProcessor.class.getName();
        if (registry.containsBeanDefinition(beanName)) {
            return registry.getBeanDefinition(beanName);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyMockPostProcessor.class.getName());
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        return builder.getBeanDefinition();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) beanFactory;

        setAllBeans(beanRegistry);
        processMyMock();
        processMyMockBean();
        processMyMockExclude();

        if (!CollectionUtils.isEmpty(mockBeans)) {
            for (Map.Entry<String, MyMockDefinition> entry : mockBeans.entrySet()) {
                String beanName = entry.getKey();
                MyMockDefinition ambd = entry.getValue();
                if (beanRegistry.containsBeanDefinition(beanName)) {
                    beanRegistry.removeBeanDefinition(beanName);
                }
                BeanDefinition beanDefinition = getMockBeanDefinition(ambd);
                beanRegistry.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    private void setAllBeans(BeanDefinitionRegistry beanRegistry) {
        for (String beanName : beanRegistry.getBeanDefinitionNames()) {
            BeanDefinition definition = beanRegistry.getBeanDefinition(beanName);
            MyMockDefinition ambd = getMockDefinition(beanName, definition);
            this.allBeans.put(beanName, ambd);
        }
    }

    private void processMyMock() {
        if (CollectionUtils.isEmpty(this.myMocks)) {
            this.myMocks = new LinkedHashSet<>();
        }
        for (String beanName : this.allBeans.keySet()) {
            MyMockDefinition ambd = this.allBeans.get(beanName);
            String beanClass = ambd.getBeanClass();
            if (beanClass != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClass, this.classLoader);
                Set<MyMockRule> myMocks = AnnotationUtils.getRepeatableAnnotations(clazz, MyMockRule.class, MyMockRules.class);
                if (!CollectionUtils.isEmpty(myMocks)) {
                    this.myMocks.addAll(myMocks);
                }
            }
        }
        for (MyMockRule myMock : myMocks) {
            if (!myMock.enabled() || !myMock.enabledStr().equalsIgnoreCase("true")) {
                continue;
            }
            processMyMockBean(myMock);
        }
    }

    private void processMyMockBean() {
        if (CollectionUtils.isEmpty(this.myMockBeans)) {
            this.myMockBeans = new LinkedHashSet<>();
        }
        for (String beanName : this.allBeans.keySet()) {
            MyMockDefinition ambd = this.allBeans.get(beanName);
            String beanClass = ambd.getBeanClass();
            if (beanClass != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClass, this.classLoader);
                Set<MyMockBean> myMockBeans = AnnotationUtils.getRepeatableAnnotations(clazz, MyMockBean.class, MyMockBeans.class);
                if (!CollectionUtils.isEmpty(myMockBeans)) {
                    this.myMockBeans.addAll(myMockBeans);
                }
            }
        }
        for (MyMockBean myMockBean : myMockBeans) {
            MyMockDefinition ambd = new MyMockDefinition();
            ambd.setBeanName(myMockBean.name());
            ambd.setBeanClass(myMockBean.clazz().getName());
            ambd.setFactoryClass(MyMockBean.class.getName());
            this.mockBeans.put(myMockBean.name(), ambd);
        }
    }

    private void processMyMockExclude() {
        if (CollectionUtils.isEmpty(this.myMockExcludes)) {
            this.myMockExcludes = new LinkedHashSet<>();
        }
        for (String beanName : this.allBeans.keySet()) {
            MyMockDefinition ambd = this.allBeans.get(beanName);
            String beanClass = ambd.getBeanClass();
            if (beanClass != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClass, this.classLoader);
                Set<MyMockExclude> myMockExcludes = AnnotationUtils.getRepeatableAnnotations(clazz, MyMockExclude.class, MyMockExcludes.class);
                if (!CollectionUtils.isEmpty(myMockExcludes)) {
                    this.myMockExcludes.addAll(myMockExcludes);
                }
            }
        }
        for (MyMockExclude myMockExclude : myMockExcludes) {
            processMyMockExcludeBean(myMockExclude);
        }
    }

    private void processMyMockBean(MyMockRule myMock) {
        List<String> beanNames = Arrays.asList(myMock.beanName());
        List<String> beanClasses = convert(myMock.beanClass(), Class::getName);
        List<String> factoryClasses = convert(myMock.factoryClass(), Class::getName);
        List<String> annotations = convert(myMock.annotation(), Class::getName);
        String expression = myMock.expression();

        for (String beanName : this.allBeans.keySet()) {
            MyMockDefinition ambd = this.allBeans.get(beanName);
            if (isMatch(ambd, beanNames, beanClasses, factoryClasses, annotations, expression)) {
                this.mockBeans.put(beanName, ambd);
            }
        }
    }

    private void processMyMockExcludeBean(MyMockExclude myMockExclude) {
        List<String> beanNames = Arrays.asList(myMockExclude.beanName());
        List<String> beanClasses = convert(myMockExclude.beanClass(), Class::getName);
        List<String> factoryClasses = convert(myMockExclude.factoryClass(), Class::getName);
        List<String> annotations = convert(myMockExclude.annotation(), Class::getName);
        String expression = myMockExclude.expression();

        for (String beanName : this.mockBeans.keySet()) {
            MyMockDefinition ambd = this.mockBeans.get(beanName);
            if (isMatch(ambd, beanNames, beanClasses, factoryClasses, annotations, expression)) {
                this.mockBeans.remove(beanName);
            }
        }
    }

    private boolean isMatch(MyMockDefinition ambd,
                            List<String> beanNames,
                            List<String> beanClasses,
                            List<String> factoryClasses,
                            List<String> annotations,
                            String expression) {
        if (beanNames.contains(ambd.getBeanName())) {
            return true;
        }

        if (beanClasses.contains(ambd.getBeanClass())) {
            return true;
        }

        if (factoryClasses.contains(ambd.getFactoryClass())) {
            return true;
        }

        for (String annotation : annotations) {
            if (!CollectionUtils.isEmpty(ambd.getAnnotations()) && ambd.getAnnotations().contains(annotation)) {
                return true;
            }
        }

        if (!StringUtils.isEmpty(expression)) {
            DefaultContext<String, Object> context = new DefaultContext<>();
            BeanMap beanMap = BeanMap.create(ambd);
            beanMap.forEach((k, v) -> context.put(k.toString(), v));
            List<String> errorList = new ArrayList<>();
            try {
                Object rs = expressRunner.execute(expression, context, errorList, true, false);
                if (Boolean.TRUE.equals(rs)) {
                    return true;
                }
            } catch (Exception e) {
                log.warn("execute expression error: \n{}\nparams: {}", expression, context, e);
            }
        }
        return false;
    }

    private <T, R> List<R> convert(T[] list, Function<T, R> fun) {
        return Arrays.stream(list).map(fun).collect(Collectors.toList());
    }

    private MyMockDefinition getMockDefinition(String beanName, BeanDefinition beanDefinition) {
        MyMockDefinition ambd = new MyMockDefinition();
        ambd.setBeanName(beanName);
        ambd.setBeanClass(beanDefinition.getBeanClassName());
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            AnnotatedBeanDefinition ab = (AnnotatedBeanDefinition) beanDefinition;
            MethodMetadata factoryMeta = ab.getFactoryMethodMetadata();
            if (factoryMeta != null) {
                String beanClass = getBeanRealClass(factoryMeta.getReturnTypeName());
                ambd.setBeanClass(beanClass);
                ambd.setFactoryClass(factoryMeta.getDeclaringClassName());
                try {
                    Class<?> clazz = ClassUtils.forName(factoryMeta.getDeclaringClassName(), classLoader);
                    ReflectionUtils.doWithMethods(clazz, m -> {
                        if (m.getName().equals(factoryMeta.getMethodName())) {
                            Annotation[] anns = AnnotationUtils.getAnnotations(m);
                            List<String> annList = convert(anns, ann -> ann.annotationType().getName());
                            ambd.setAnnotations(annList);
                        }
                    });
                } catch (Exception e) {
                    log.warn("error set annotation", e);
                }
                //把所有注解的属性拿出来
                for (String annotation : ambd.getAnnotations()) {
                    AnnotationAttributes atts = (AnnotationAttributes) factoryMeta.getAnnotationAttributes(annotation);
                    ambd.getAttributesMap().add(annotation, atts);
                }
            } else if (ab.getMetadata() != null) {
                Set<String> annTypes = ab.getMetadata().getAnnotationTypes();
                ambd.setAnnotations(new ArrayList<>(annTypes));
            }
        }
        return ambd;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private BeanDefinition getMockBeanDefinition(MyMockDefinition ambd) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyMockBeanFactory.class);
        builder.addPropertyValue("beanClass", ambd.getBeanClass());
        return builder.getBeanDefinition();
    }

    private String getBeanRealClass(String realClass) {
        try {
            Class<?> realClazz = Class.forName(realClass);
            if (ClassUtils.isAssignable(FactoryBean.class, realClazz)) {
                Method getObject = ClassUtils.getMethod(realClazz, "getObject");
                if (getObject != null) {
                    return getObject.getReturnType().getName();
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("can't load class {}", realClass, e);
        }
        return realClass;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
