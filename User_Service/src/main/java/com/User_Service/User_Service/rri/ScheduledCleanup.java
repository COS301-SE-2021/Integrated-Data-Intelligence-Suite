package com.User_Service.User_Service.rri;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class ScheduledCleanup {
    private static final Logger log = LoggerFactory.getLogger(ScheduledCleanup.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Async
    @Scheduled(cron = "0 0 4 * * *")
    public void fetchData() {
        log.info("Performing database cleanup, current time is: {}", dateFormat.format(new Date()));
    }
}

