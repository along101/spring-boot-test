package com.yzl.spi;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class BinderSupporter {
    public static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not create instance by class %s error.", clazz), e);
        }
    }

    public static <T> T generate(Class<T> clazz) {
        BinderDefine<?> binderDefine = BinderDefines.getInstance().getBinderDefine(clazz, "DEFAULT");
        if (binderDefine == null) {
            return null;
        }
        return (T) newInstance(binderDefine.getBinderClass());
    }

    public static <T> T generate(Class<T> clazz, String name) {
        BinderDefine<?> binderDefine = BinderDefines.getInstance().getBinderDefine(clazz, name);
        if (binderDefine == null) {
            return null;
        }
        return (T) newInstance(binderDefine.getBinderClass());
    }


    public static Set<Type> getAllSuper(Class<?> clazz) {
        Set<Type> superTypes = new LinkedHashSet<>(Arrays.asList(clazz.getGenericInterfaces()));
        if (clazz.getGenericSuperclass() != null) {
            superTypes.add(clazz.getGenericSuperclass());
        }
        for (Type superType : superTypes) {
            Class<?> typeClass = getTypeClass(superType);
            if (typeClass != null) {
                superTypes.addAll(getAllSuper(typeClass));
            }
        }
        return superTypes;
    }

    public static Class<?> getTypeClass(Type type) {
        Class<?> typeClass = null;
        if (type instanceof Class) {
            typeClass = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                typeClass = (Class<?>) rawType;
            }
        }
        return typeClass;
    }
}
