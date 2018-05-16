package com.yzl.spring.test;

import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yinzuolong
 */
public class MyBeanWrapper extends BeanWrapperImpl {


    public List<String> getValues() {
        List<String> s = new LinkedList<>();
        Object w = this.getWrappedInstance();
        if (w == null) {
            return s;
        }
        PropertyDescriptor[] pds = this.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            if (Map.class.isAssignableFrom(pds[i].getPropertyType())) {
                test(pds[i]);
            } else if (List.class.isAssignableFrom(pds[i].getPropertyType())) {
                test(pds[i]);
            } else if (Bar.class.isAssignableFrom(pds[i].getPropertyType())) {
                test(pds[i]);
            }

        }
        return s;
    }

    private void test(PropertyDescriptor pd) {

        AbstractNestablePropertyAccessor pa = this.getPropertyAccessorForPropertyPath("." + pd.getName());

        String nestPath = pa.getNestedPath();
        Object value = this.getPropertyValue(pd.getName());
        pa.setWrappedInstance(value);

    }
}
