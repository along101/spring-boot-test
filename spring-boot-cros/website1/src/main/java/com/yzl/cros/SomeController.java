package com.yzl.cros;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yutu
 * @date 2018/12/28
 */
@RestController
public class SomeController {

    public static String getCookie(HttpServletRequest request) {
        String cookies = null;
        if (request.getCookies() != null) {
            cookies = Arrays.stream(request.getCookies())
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue()).collect(Collectors.joining(","));
        }
        return cookies;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        //写入cookie
        response.addCookie(new Cookie("site1", "abc"));
        return "test-" + new Date() + ",cookie:" + getCookie(request);
    }

    @GetMapping("/get")
    public String get(HttpServletRequest request) {
        return "test-" + new Date() + ",cookie:" + getCookie(request);
    }

    @PostMapping("/post")
    public String post(HttpServletRequest request, @RequestBody String data) {
        return data + ",test-" + new Date() + ",cookie:" + getCookie(request);
    }

    @PostMapping("/postjson")
    public Map<String, String> postjson(HttpServletRequest request, @RequestBody Map<String, String> map) {
        System.out.println(map);
        map.put("result", "test-" + new Date() + ",cookie:" + getCookie(request));
        return map;
    }

}
