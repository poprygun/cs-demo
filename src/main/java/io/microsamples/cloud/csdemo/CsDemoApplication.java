package io.microsamples.cloud.csdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsDemoApplication.class, args);
    }

}

@RestController
class PropertiesServer {

    @Value("${location:mars}")
    private String location;

    @GetMapping(value = "where")
    private String serveWhere(){
        return location;
    }

}

@Configuration
class Show{

    @Bean
    public String showMe(@Value("${location:mars}") String location){
        System.out.println("=========================> " + location);
        return location;
    }

}


