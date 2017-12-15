package com.yzl.spi;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;

/**
 * Created by yinzuolong on 2017/12/6.
 */
public abstract class BindersScanner {
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/binder.factories";

    public static Map<Class<?>, List<Class<?>>> scanPrpcBinders(List<String> pakages) {
        final List<Class<?>> spiInterfaces = new ArrayList<>();
        final List<Class<?>> prpcBinderClasses = new ArrayList<>();
        new FastClasspathScanner(pakages.toArray(new String[0])).matchClassesWithAnnotation(Spi.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                spiInterfaces.add(aClass);
            }
        }).matchClassesWithAnnotation(SpiBinder.class, new ClassAnnotationMatchProcessor() {
            @Override
            public void processMatch(Class<?> aClass) {
                prpcBinderClasses.add(aClass);
            }
        }).scan();

        Map<Class<?>, List<Class<?>>> bindersMap = new HashMap<>();
        for (Class<?> prpcBinderClass : prpcBinderClasses) {
            if (prpcBinderClass.isInterface())
                continue;
            for (Class<?> spiInterface : spiInterfaces) {
                if (!spiInterface.isInterface())
                    continue;
                if (spiInterface.isAssignableFrom(prpcBinderClass)) {
                    List<Class<?>> values = bindersMap.get(spiInterface);
                    if (values == null) {
                        values = new ArrayList<>();
                        bindersMap.put(spiInterface, values);
                    }
                    values.add(prpcBinderClass);
                }
            }
        }
        return bindersMap;
    }

    public static Map<Class<?>, List<Class<?>>> scanBinders() {
        return scanPrpcBinders(new ArrayList<>());
    }


    public static Map<Class<?>, List<Class<?>>> loadBinders(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = BindersScanner.class.getClassLoader();
        }
        ClassLoader classLoaderToUse = classLoader;
        try {
            Enumeration<URL> urls = (classLoaderToUse != null ? classLoaderToUse.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
            Map<Class<?>, List<Class<?>>> bindersMap = new LinkedHashMap<>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Properties properties = new Properties();
                properties.load(url.openStream());
                for (String key : properties.stringPropertyNames()) {
                    Class<?> spiInterface = loadClass(key, classLoaderToUse);
                    String[] strs = properties.getProperty(key).split(",");
                    List<String> binders = Arrays.asList(strs);
                    List<Class<?>> binderClasses = new ArrayList<>();
                    for (Object binder : binders) {
                        Class<Object> binderClass = loadClass(binder.toString(), classLoaderToUse);
                        binderClasses.add(binderClass);
                    }
                    bindersMap.put(spiInterface, binderClasses);

                }
            }
            return bindersMap;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unable to load  binders from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
        }
    }


    private static <T> Class<T> loadClass(String className, ClassLoader classLoader) {
        if (StringUtils.isBlank(className)) {
            return null;
        }
        Class<T> clz;
        try {
            clz = (Class<T>) Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    String.format("Class %s not found.", className));
        }
        return clz;
    }

}
