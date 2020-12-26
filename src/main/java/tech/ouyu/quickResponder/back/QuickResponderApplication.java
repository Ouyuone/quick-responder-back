package tech.ouyu.quickResponder.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"tech.ouyu.quickResponder.back.mapper"})
@SpringBootApplication
public class QuickResponderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickResponderApplication.class, args);
    }

}
