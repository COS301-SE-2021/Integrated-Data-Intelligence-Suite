package com.User_Service.User_Service.service;

import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.request.SendEmailNotificationRequest;
import com.User_Service.User_Service.response.SendEmailNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

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
    public CompletableFuture<SendEmailNotificationResponse> sendEmailNotification(SendEmailNotificationRequest request) throws Exception {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }
        //System.out.println("Sending notification to user.");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(request.getFrom());
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getText());

        try {
            emailSender.send(message);
        } catch (MailException e) {
            return CompletableFuture.completedFuture(new SendEmailNotificationResponse(false, "An error has occurred while sending an the activation code to user."));
        }

        return CompletableFuture.completedFuture(new SendEmailNotificationResponse(true, "Successfully sent notification"));
    }
}
