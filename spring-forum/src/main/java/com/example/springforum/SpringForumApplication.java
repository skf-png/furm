package com.example.springforum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springforum.mapper")
public class SpringForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringForumApplication.class, args);
    }
}
