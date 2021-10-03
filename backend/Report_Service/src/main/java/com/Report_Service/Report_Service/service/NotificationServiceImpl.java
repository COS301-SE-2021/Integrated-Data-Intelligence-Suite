package com.Report_Service.Report_Service.service;

import com.Report_Service.Report_Service.exception.InvalidRequestException;
import com.Report_Service.Report_Service.request.SendEmailReportRequest;
import com.Report_Service.Report_Service.response.SendEmailReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
@Configuration
public class NotificationServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    public NotificationServiceImpl() {

    }

    @Async
    public CompletableFuture<SendEmailReportResponse> shareReportViaEmail(SendEmailReportRequest request) throws Exception {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }
        System.out.println("Sending email to " + request.getTo());
        System.out.println("Sending email from " + request.getFrom());

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(request.getTo());
        helper.setFrom(request.getFrom());
        helper.setSubject(request.getSubject());
        helper.addAttachment("report", new ByteArrayResource(request.getData()));

        try {
            System.out.println("Sending email...");
            emailSender.send(message);
            System.out.println("Sent email");
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(new SendEmailReportResponse(false, "An error has occurred while sending an the activation code to user."));
        }

        return CompletableFuture.completedFuture(new SendEmailReportResponse(true, "Successfully sent notification"));
    }
}
