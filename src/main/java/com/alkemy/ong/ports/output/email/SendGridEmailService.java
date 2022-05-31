package com.alkemy.ong.ports.output.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendGridEmailService implements EmailService {

    private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@somosmas.org";
    private final SendGrid sendGridClient;
    private static final String templateId = "5ba54d09-0af1-4648-8c64-940509a7c5e9";
    @Value("${email.from}")
    private String emailFrom;

    @Override
    public void sendText(String to, String subject, String body) {
        sendEmail(this.emailFrom, to, subject, new Content("text/plain", body));
    }

    @Override
    public void sendHTML(String to, String subject, String body) {
        sendEmail(this.emailFrom, to, subject, new Content("text/html", templateId));
    }

    private void sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("/mail/send");
            request.setBody(mail.build());
            this.sendGridClient.api(request);
        } catch (IOException ex) {
            log.error("Error sending email", ex);
            throw new RuntimeException(ex);
        }
    }

    private void sendWelcomeEmail(String to, String subject, String body){
        subject = "Thanks for being part of Somos Mas";
        this.sendHTML(to, subject, body);
    }

}
