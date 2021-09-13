package com.User_Service.User_Service.request;

public class VerifyAccountRequest {
    private String email;
    private String verificationCode;

    public VerifyAccountRequest() {

    }

    public VerifyAccountRequest(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
