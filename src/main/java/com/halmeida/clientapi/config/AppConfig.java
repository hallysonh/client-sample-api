package com.halmeida.clientapi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final AppProperties appProperties;

    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public FilterRegistrationBean<ApiTokenFilter> loggingFilter(){
        FilterRegistrationBean<ApiTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ApiTokenFilter(appProperties));
        registrationBean.addUrlPatterns("/client/*");
        registrationBean.addUrlPatterns("/clients/*");

        return registrationBean;
    }
}
