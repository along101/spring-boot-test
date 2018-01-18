package com.yzl.spring.event.controller;

import com.yzl.spring.event.event.ConditionalEvent;
import com.yzl.spring.event.event.Event;
import com.yzl.spring.event.event.EventExtendsClass;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class TestController implements ApplicationEventPublisherAware{

    /*�㲥��ʵ�ַ�ʽһ
    (���ӿ�ApplicationEventPublisherAware����ApplicationContextAware)*/
    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /*�㲥��ʵ�ַ�ʽ��(����ӿ�)*/
    /* @Autowired
    ApplicationEventPublisher applicationEventPublisher;*/

    @ResponseBody
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String everyone(@RequestParam(value = "keyword", defaultValue = " everyone") String keyword) {
        if(keyword.equals("world")){
            applicationEventPublisher.publishEvent(new EventExtendsClass(this,"world"));
            return "hello world";
        }
        applicationEventPublisher.publishEvent(new Event(this,"world"));
        return "hello everyone";
    }

    @ResponseBody
    @RequestMapping(value = "/condition")
    public String condition(@RequestParam(value = "isImportant", defaultValue = "false")
                                        String isImportant,
                            @RequestParam(value = "details"    , defaultValue = "Here is the detail.")
                                    String details     ) {
        if( "true".equals(isImportant.trim().toLowerCase()) )
            applicationEventPublisher.
                    publishEvent(new ConditionalEvent(details,true));
        else
            applicationEventPublisher.
                    publishEvent(new ConditionalEvent(details,false));
        return "hello condition";
    }


}