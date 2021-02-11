package com.ccand99.awesome.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@SpringBootApplication
public class Application {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
