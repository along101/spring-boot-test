package com.yzl.spi;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BinderFactorySupporter {

    public static <T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Can not create instance by class %s error.", clazz), e);
        }
    }

    /**
     * 获取指定类继承父类的泛型参数，ArrayList<String>  继承 List<String>，这里获取的是String
     *
     * @param clazz
     * @param superClass
     * @return
     */
    public static List<Class<?>> getGenericParameter(Class<?> clazz, Class<?> superClass) {
        List<Class<?>> generics = new ArrayList<>();
        //获取实现的所有接口
        Type[] types = clazz.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                Class<?> factoryInterface = (Class<?>) ptype.getRawType();
                //这里才找到
                if (factoryInterface.getName().equals(superClass.getName())) {
                    for (Type typeArgument : ptype.getActualTypeArguments()) {
                        Class<?> genericClass = (Class<?>) ptype.getActualTypeArguments()[0];
                        generics.add(genericClass);
                    }
                }
            }
        }
        return generics;
    }

    //TODO 获取类所有父类和接口
    public static Set<Class<?>> getAllSuper(Class<?> clazz) {
        return null;
    }
}
