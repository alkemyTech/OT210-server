package com.alkemy.ong;

import com.alkemy.ong.ports.output.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
class EmailTest {

    @Autowired
    EmailService sendGridEmailService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmailTest.class);
        app.run(args);
        Email from = new Email(System.getenv("SG_EMAILFROM"));
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("nicouemacapdevila@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SG_APIKEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("FAiled" + ex.getMessage());;
        }
    }
//
//    @Override
//    public void run(String... arg0) throws IOException, URISyntaxException {
//        this.sendGridEmailService.sendHTML("${SG_EMAILFROM}", "nicouemacapdevila@gmail.com",
//                "Hello World", "Hello, <strong>how are you doing?</strong>");
//    }
}
