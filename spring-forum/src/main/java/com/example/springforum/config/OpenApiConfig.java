package com.example.springforum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Forum API") // 文档标题
                        .version("1.0.0")          // 版本号
                        .description("论坛系统前后端分离 API 文档") // 描述
                        .contact(new Contact()     // 联系人信息
                                .name("技术团队")
                                .url("https://example.com")
                                .email("dev@example.com")
                        )
                );
    }
}