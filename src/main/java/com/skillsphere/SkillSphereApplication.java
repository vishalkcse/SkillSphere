package com.skillsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SkillSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillSphereApplication.class, args);
    }
}
