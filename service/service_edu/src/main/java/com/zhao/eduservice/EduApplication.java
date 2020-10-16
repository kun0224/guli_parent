package com.zhao.eduservice;

import com.zhao.eduservice.mapper.EduSubjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@ComponentScan(basePackages = {"com.zhao"})
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);

    }
}
