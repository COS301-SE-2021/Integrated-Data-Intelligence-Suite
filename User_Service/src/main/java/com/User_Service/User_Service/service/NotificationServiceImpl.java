package com.User_Service.User_Service.service;

import com.User_Service.User_Service.request.SendNotificationRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@Configuration
public class NotificationServiceImpl {

    public NotificationServiceImpl() {

    }

    @Async
    public void sendNotification(SendNotificationRequest request) throws Exception {

    }
}
