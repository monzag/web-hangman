package com.codecool.webhangman.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private SessionManager sessionManager;

    public WebConfig(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.sessionManager)
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
        // assuming you put your serve your static files with /resources/ mapping
        // and the pre login page is served with /login mapping
    }
}