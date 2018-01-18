package com.yzl.spi;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class BinderDefines {
    private static BinderDefines instance;
    private Map<Class<?>, Map<String, BinderDefine<?>>> binderDefines = new ConcurrentHashMap<>();

    public static BinderDefines getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (BinderDefines.class) {
            if (instance != null) {
                return instance;
            }
            instance = new BinderDefines();
            return instance;
        }
    }

    private BinderDefines() {
        String pkg = this.getClass().getPackage().getName();
        pkg = StringUtils.substringBeforeLast(pkg, ".");
        doScan(Arrays.asList(pkg));
    }

    public void doScan(List<String> packages) {
        Map<Class<?>, List<Class<?>>> binderMap = BindersScanner.scanPrpcBinders(packages);
        for (Class<?> interfaceClass : binderMap.keySet()) {
            List<Class<?>> binderClasses = binderMap.get(interfaceClass);
            for (Class binderClass : binderClasses) {
                BinderDefine<?> binderDefine = createBinderDefine(interfaceClass, binderClass);
                registerBinderDefine(binderDefine);
            }
        }
    }

    public <T> void registerBinderDefine(BinderDefine<T> binderDefine) {
        BinderDefine<T> newBinderfine = new BinderDefine<>();
        newBinderfine.setInterfaceClass(binderDefine.getInterfaceClass());
        newBinderfine.setName(StringUtils.isBlank(binderDefine.getName()) ? "DEFAULT": binderDefine.getName());
        newBinderfine.setBinderClass(binderDefine.getBinderClass());
        newBinderfine.setScope(binderDefine.getScope() == null ? Scope.SINGLETON : binderDefine.getScope());
        newBinderfine.setOrder(binderDefine.getOrder());
        Map<String, BinderDefine<?>> binderMap = binderDefines.get(binderDefine.getInterfaceClass());
        if (binderMap == null) {
            binderMap = new ConcurrentHashMap<>();
            binderDefines.put(binderDefine.getInterfaceClass(), binderMap);
        }
        if (binderMap.containsKey(newBinderfine.getName())) {
            BinderDefine<?> cached = binderMap.get(newBinderfine.getName());
            if (!cached.equals(newBinderfine)) {
                throw new RuntimeException(String.format("Interface %s binder name=%s aready exist with class %s,can not bind class %s.",
                        newBinderfine.getInterfaceClass(), newBinderfine.getName(), newBinderfine.getBinderClass(), cached.getBinderClass()));
            }
            return;
        }
        binderMap.put(newBinderfine.getName(), newBinderfine);
    }

    public <T> BinderDefine<T> getBinderDefine(Class<T> interfaceClass, String name) {
        Map<String, BinderDefine<?>> map = this.binderDefines.get(interfaceClass);
        if (map != null) {
            return (BinderDefine<T>) map.get(name);
        }
        return null;
    }

    public <T> BinderDefine<T> getBinderDefine(Class<T> interfaceClass) {
        return getBinderDefine(interfaceClass, "DEFAULT");
    }


    public static <T> BinderDefine<T> createBinderDefine(Class<T> interfaceClass, Class<? extends T> binderClass) {
        Spi spi = interfaceClass.getAnnotation(Spi.class);
        SpiBinder prpcBinder = binderClass.getAnnotation(SpiBinder.class);

        BinderDefine<T> binderDefine = new BinderDefine<>();
        binderDefine.setInterfaceClass(interfaceClass);
        binderDefine.setBinderClass(binderClass);
        binderDefine.setScope(spi == null ? Scope.SINGLETON : spi.scope());
        binderDefine.setName(prpcBinder == null ? "DEFAULT" : prpcBinder.name());
        binderDefine.setOrder(prpcBinder == null ? 0 : prpcBinder.order());
        return binderDefine;
    }
}
