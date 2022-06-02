package com.alkemy.ong.ports.output.email;

import com.alkemy.ong.domain.model.Organization;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendGridEmailService implements EmailService {

    private final SendGrid sendGridClient;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.welcomeSubject}")
    private String welcomeSubject;

    @Value("${email.sendgrid.template}")
    private String templateId;

    private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@somosmas.org";

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

    @Override
    public void sendWelcomeEmail(String to){
        Mail mail = new Mail();
        mail.setFrom(new Email(this.emailFrom));
        mail.setSubject(this.welcomeSubject);
        Personalization p= new Personalization();
        p.addTo(new Email(to));
 feature/OT210-29
        Organization organization = new Organization();
        String image = organization.getImage();
        p.addDynamicTemplateData("image", image);
        String name = organization.getName();
        p.addDynamicTemplateData("name", name);
        String welcome_text = organization.getWelcomeText();
        p.addDynamicTemplateData("welcome_text", welcome_text);
        mail.addPersonalization(p);
=======
        main
        mail.setTemplateId(this.templateId);
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

}
