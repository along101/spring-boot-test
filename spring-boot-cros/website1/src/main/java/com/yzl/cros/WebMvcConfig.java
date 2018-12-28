package com.yzl.cros;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yutu
 * @date 2018/12/28
 */
@Configuration
@ConfigurationProperties(prefix = "cros")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Setter
    @Getter
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (StringUtils.isEmpty(allowedOrigins)) {
            return;
        }
        String[] allowedOriginArray = allowedOrigins.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedOriginArray)
                .allowedMethods("PUT", "DELETE", "GET", "POST", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                .allowCredentials(true)
                .maxAge(3600);

    }
}
