package com.User_Service.User_Service.request;

public class ResendCodeRequest {
    private String email;

    public ResendCodeRequest() {

    }

    public ResendCodeRequest(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
