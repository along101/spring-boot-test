package com.yzl.spi;

public interface BinderFactory<T> {

    T generate(String name);

    T generate();
}
