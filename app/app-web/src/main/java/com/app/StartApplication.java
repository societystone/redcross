package com.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot启动类 . <br>
 * 继承SpringBootServletInitializer类并重写configure方法 <br>
 * 打包成war包的形式 <br>
 * ServletComponentScan表示开启servlet的注解 <br>
 *
 * @author wangtw <br>
 */
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.app.dao")
public class StartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StartApplication.class);
    }

    /**
     * 启动入口
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
