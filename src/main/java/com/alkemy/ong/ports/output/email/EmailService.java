package com.alkemy.ong.ports.output.email;

import com.alkemy.ong.domain.model.Organization;

public interface EmailService {
    void sendText(String to, String subject, String body);
    void sendHTML(String to, String subject, String body);
    void sendWelcomeEmail(String to, Organization organization);
}
