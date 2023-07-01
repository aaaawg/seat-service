package com.psr.seatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //파일 저장 위치 **
        registry.addResourceHandler( "/files/**")
                .addResourceLocations("file:///C:/Users/dkdud/Downloads/seat-service-master/seat-service/files/");
    }
}