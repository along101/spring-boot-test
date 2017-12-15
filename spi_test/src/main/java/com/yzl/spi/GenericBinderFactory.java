package com.yzl.spi;


import java.util.List;

public abstract class GenericBinderFactory<T> implements BinderFactory<T> {
    private T singleton;

    @Override
    public T generate(String name) {
        List<Class<?>> genercClasses = BinderFactorySupporter.getGenericParameter(this.getClass(), BinderFactory.class);
        if (genercClasses.size() == 0)
            return null;
        BinderDefine<T> binderDefine = BinderDefines.getInstance().getBinderDefine((Class<T>) genercClasses.get(0), name);
        if (binderDefine == null) {
            return null;
        }
        if (binderDefine.getScope() == Scope.PROTOTYPE) {
            return BinderFactorySupporter.newInstance(binderDefine.getBinderClass());
        } else {
            if (this.singleton != null) {
                return singleton;
            }
            synchronized (this) {
                if (singleton != null) {
                    return singleton;
                }
                singleton = BinderFactorySupporter.newInstance(binderDefine.getBinderClass());
            }
            return singleton;
        }
    }

    @Override
    public T generate() {
        return generate("DEFAULT");
    }
}
