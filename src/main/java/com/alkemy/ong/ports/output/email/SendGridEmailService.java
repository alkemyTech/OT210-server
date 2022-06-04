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

    @Value("${email.welcomeSubject}")
    private String welcomeSubject;

    @Value("${email.sendgrid.template}")
    private String templateId;

    private static final String NO_REPLY_SOMOSMAS_ORG = "no-reply@somosmas.org";

    @Override
    public void sendText(String from, String to, String subject, String body) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), new Content("text/plain", body));
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        send(mail);
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), new Content("text/html", body));
        mail.setReplyTo(new Email(NO_REPLY_SOMOSMAS_ORG));
        send(mail);
    }

    @Override
    public void sendWelcomeEmail(String to, Organization organization) {
        Mail mail = new Mail();
        mail.setSubject(this.welcomeSubject);
        mail.setTemplateId(this.templateId);

        Email from = new Email(organization.getEmail());
        mail.setFrom(from);

        Email replyTo = new Email(NO_REPLY_SOMOSMAS_ORG);
        mail.setReplyTo(replyTo);

        Email userEmail = new Email(to);
        Personalization personalization = personalization(userEmail, organization);
        mail.addPersonalization(personalization);

        send(mail);
    }

    private void send(Mail mail) {
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

    private Personalization personalization(Email userEmail, Organization organization) {
        Personalization personalization;
        personalization = new Personalization();
        personalization.addTo(userEmail);
        personalization.addDynamicTemplateData("image", organization.getImage());
        personalization.addDynamicTemplateData("name", organization.getName());
        personalization.addDynamicTemplateData("welcome_text", organization.getWelcomeText());
        personalization.addDynamicTemplateData("email", organization.getEmail());
        personalization.addDynamicTemplateData("phone", organization.getPhone());
        personalization.addDynamicTemplateData("linkedin", organization.getLinkedinContact());
        personalization.addDynamicTemplateData("facebook", organization.getFacebookContact());
        personalization.addDynamicTemplateData("instagram", organization.getInstagramContact());
        return personalization;
    }

}
