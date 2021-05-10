package com.nuange;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.nuange.dao")
@ComponentScan(basePackages = {"com.n3r.idworker","com.nuange"})
@SpringBootApplication
public class ImBirdSysApplication {

    //注册SpringUtil
    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(ImBirdSysApplication.class, args);
    }

}
