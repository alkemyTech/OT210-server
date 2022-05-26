package com.alkemy.ong;

import com.alkemy.ong.ports.output.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
class EmailTest implements CommandLineRunner {

    @Autowired
    EmailService sendGridEmailService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmailTest.class);
        app.run(args);
    }

    @Override
    public void run(String... arg0) throws IOException, URISyntaxException {
        this.sendGridEmailService.sendText(System.getenv("SG_EMAILFROM"), "nicouemacapdevila@gmail.com",
                "Hello World", "2- Hello, <strong>how are you doing?</strong>");
    }
}