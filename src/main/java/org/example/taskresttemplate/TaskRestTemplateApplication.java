package org.example.taskresttemplate;

import org.example.taskresttemplate.service.RestApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskRestTemplateApplication implements CommandLineRunner {

    private final RestApiClient restApiClient;

    @Autowired
    public TaskRestTemplateApplication(RestApiClient restApiClient) {
        this.restApiClient = restApiClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskRestTemplateApplication.class, args);
    }

    @Override
    public void run(String... args) {
        restApiClient.execute();
    }
}
