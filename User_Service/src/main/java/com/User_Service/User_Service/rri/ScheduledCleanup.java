package com.User_Service.User_Service.rri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.User_Service.User_Service.config.AccountConfig;
import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class ScheduledCleanup {
    private static final Logger log = LoggerFactory.getLogger(ScheduledCleanup.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private AccountConfig accConfig;

    @Autowired
    private UserRepository repository;

    /**
     * This function is used to clean up the database for all users that have not
     * verified their within the specified time.
     * This action is performed everyday at the specified chronological time.
     * wait time before account deletion - 1 day(s)
     */
    @Async
    @Scheduled(cron = "0 43 17 * * *")
    public void performCleanup() {
        log.info("Performing database cleanup, current time is: {}", dateFormat.format(new Date()));
        List<User> users = repository.findAllByIsVerifiedFalseOrderByDateCreatedAsc();
        //Get current time
        Date now = new Date();
        for(User u : users) {
            //System.out.println(u.getEmail() + " date registered: " + u.getDateCreated());
            //Calculate difference
            long difference_In_Time = now.getTime() - u.getDateCreated().getTime();
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            if(difference_In_Days > accConfig.getMaxWaitInDays()) {
                System.out.println("User to be deleted: " + u.getEmail());
                repository.delete(u);
            }
        }
        log.info("Completed removal of unverified accounts");
    }
}

