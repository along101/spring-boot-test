package com.yzl.spring.test.tool.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yzl.spring.test.tool.data.bean.MyDemo1;
import com.yzl.spring.test.tool.data.bean.MyDemoBean;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yutu
 * @date 2021-02-03
 */
public class CaseDataUtilTest {

    @Test
    public void testReadCaseJSON() {
        JSONObject json = CaseDataUtil.readCaseJSON("case/test/test.json");
        String text = JSON.toJSONString(json, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(text);
        Assert.assertFalse(text.contains("$case"));
    }

    @Test
    public void testReadCaseObject() {
        MyDemoBean myDemoBean = CaseDataUtil.readCaseObject("case/test/test.json", MyDemoBean.class);
        System.out.println(JSON.toJSONString(myDemoBean, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName));
        Assert.assertNotNull(myDemoBean);
        Assert.assertNotNull(myDemoBean.getDemo1());
        Assert.assertNotNull(myDemoBean.getDemo2());
        Assert.assertNotNull(myDemoBean.getDemos());
        Assert.assertEquals(2, myDemoBean.getDemos().size());
    }

    @Test
    public void testMap() {
        MyDemoBean myDemoBean = CaseDataUtil.readCaseObject("case/test/testMap.json", MyDemoBean.class);
        Assert.assertNotNull(myDemoBean.getDemoMap());
        Assert.assertNotNull(myDemoBean.getDemoMap().get("demo1"));
        System.out.println(myDemoBean.getDemoMap().get("demo1").getClass());
        Assert.assertEquals(MyDemo1.class, myDemoBean.getDemoMap().get("demo1").getClass());
    }

    @Test
    public void testMapRef() {
        MyDemoBean myDemoBean = CaseDataUtil.readCaseObject("case/test/testMapRef.json", MyDemoBean.class);
        Assert.assertNotNull(myDemoBean.getDemoMap());
        Assert.assertNotNull(myDemoBean.getDemoMap().get("demo1"));
        System.out.println(myDemoBean.getDemoMap().get("demo1").getClass());
        Assert.assertEquals(MyDemo1.class, myDemoBean.getDemoMap().get("demo1").getClass());
    }
}
