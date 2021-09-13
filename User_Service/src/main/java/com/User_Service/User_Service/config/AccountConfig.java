package com.User_Service.User_Service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account")
public class AccountConfig {

    /**
     * The number of days to wait before a unverified user is deleted
     */
    private Integer maxWaitInDays = 1;

    public Integer getMaxWaitInDays() {
        return maxWaitInDays;
    }

    public void setMaxWaitInDays(Integer maxWaitInDays) {
        this.maxWaitInDays = maxWaitInDays;
    }
}
