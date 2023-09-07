package com.app.taptap.config;

import com.app.taptap.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private MyInterceptor myInterceptor;

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.myInterceptor)
                .addPathPatterns("/PersonInfo/**","/Community/admin/**","/Find/admin/**","/WeChat/**") // 设置拦截的请求路径
                .excludePathPatterns("/Init/**", "/RAL/**", "/Main/**","/Community/free/**","/Search/**","/Sidebar/**","/Rank/**","/Find/free/**"); // 排除不需要拦截的请求路径
    }
}
