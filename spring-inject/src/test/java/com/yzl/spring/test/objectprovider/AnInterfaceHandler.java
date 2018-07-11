package com.yzl.spring.test.objectprovider;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AnInterfaceHandler {

    @Autowired
    private ObjectProvider<List<AnInterface>> anInterfaces;

    @PostConstruct
    void init() {
        List<AnInterface> interfaces = this.anInterfaces.getIfAvailable();
        interfaces.forEach(AnInterface::doSomething);
    }
}
