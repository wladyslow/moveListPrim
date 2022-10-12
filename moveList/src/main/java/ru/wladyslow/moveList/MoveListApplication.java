package ru.wladyslow.moveList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoveListApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveListApplication.class, args);
    }

}
