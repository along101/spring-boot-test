package com.yzl.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yutu
 * @date 2018/12/25
 */
@Controller
public class MyController {

    @ResponseBody
    @RequestMapping(path = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(new Cookie("abx", "123"));
        return "123";
    }


    @RequestMapping("/t1")
    public String velocityTest(ModelMap modelMap) {
        modelMap.addAttribute("message", "这是测试的内容。。。");
        return "index";
    }
}
