package com.example.springforum.common.config;

import com.example.springforum.common.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/sign-in.html") // 排除登录HTML
                .excludePathPatterns("/sign-up.html") // 排除注册HTML
                .excludePathPatterns("/user/login") // 排除登录api接⼝
                .excludePathPatterns("/user/register") // 排除注册api接⼝
                .excludePathPatterns("/user/logout") // 排除退出api接⼝
                .excludePathPatterns("/swagger*/**") // 排除登录swagger下所有
                .excludePathPatterns("/v3*/**") // 排除登录v3下所有，与
                .excludePathPatterns("/dist/**") // 排除所有静态⽂件
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/**.ico")
                .excludePathPatterns("/js/**");
    }
}
