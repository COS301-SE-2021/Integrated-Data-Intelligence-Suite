package com.User_Service.User_Service.request;

public class ResetPasswordRequest {
    private String newPassword;
    private String email;

    public ResetPasswordRequest() {

    }

    public ResetPasswordRequest(String newPassword, String email) {
        this.newPassword = newPassword;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
