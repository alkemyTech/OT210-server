package com.alkemy.ong.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Value("${email.sendgrid.apikey}")
    String sendGridAPIKey;

    @Bean
    SendGrid getSendGridAPIKey(){
        return new SendGrid(sendGridAPIKey);
    }

}
