package org.pzz.modules;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.pzz"})
@MapperScan(basePackages = {"org.pzz.modules.mapper"})
public class PzzSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PzzSystemApplication.class, args);
    }
}
