package com.baozi.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: baozi
 * @Date: 2019/2/26 14:38
 * @Description:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        }catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
