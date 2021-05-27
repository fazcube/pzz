package org.pzz.modules;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(scanBasePackages = {"org.pzz"})
@MapperScan(basePackages = {"org.pzz.modules.mapper"})
public class PzzSystemApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(PzzSystemApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application pzz is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + contextPath + "/\n\t" +
                "Doc文档: \thttp://" + ip + ":" + port + contextPath + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
