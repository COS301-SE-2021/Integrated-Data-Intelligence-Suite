package com.User_Service.User_Service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("email")
public class ConfigProperties {
    /**
     * This is the domain that will be checked
     */
    private String emailDomain;

    /**
     * This is if the domain should be checked by the system
     */
    private boolean allowAnyDomain = false;

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public boolean isAllowAnyDomain() {
        return allowAnyDomain;
    }

    public void setAllowAnyDomain(boolean allowAnyDomain) {
        this.allowAnyDomain = allowAnyDomain;
    }
}
