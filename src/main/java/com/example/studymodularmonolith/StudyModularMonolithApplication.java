package com.example.studymodularmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;

@SpringBootApplication
@Modulith
public class StudyModularMonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyModularMonolithApplication.class, args);
    }

}
