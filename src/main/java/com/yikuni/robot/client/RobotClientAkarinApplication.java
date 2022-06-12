package com.yikuni.robot.client;

import love.forte.simboot.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSimbot
@SpringBootApplication
@EnableScheduling
public class RobotClientAkarinApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotClientAkarinApplication.class, args);
    }

}
