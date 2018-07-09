package com.yzl.spring.test;

import java.util.List;
import java.util.Map;

/**
 * @author yinzuolong
 */
public class Foo {

    private String name;

    private List<String> subList;

    private int age;

    private Bar bar;

    private Map<String, String> map1;

    private Map<String, Bar> barMap;

    private byte[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubList() {
        return subList;
    }

    public void setSubList(List<String> subList) {
        this.subList = subList;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, String> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, String> map1) {
        this.map1 = map1;
    }

    public Map<String, Bar> getBarMap() {
        return barMap;
    }

    public void setBarMap(Map<String, Bar> barMap) {
        this.barMap = barMap;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
