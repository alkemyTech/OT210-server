package com.alkemy.ong.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SendGridConfig {

    @Value("${email.sendgrid.apikey}")
    String sendGridAPIKey;

    @Bean
    public SendGrid getSendGridAPIKey(){
        return new SendGrid(sendGridAPIKey);
    }

}
