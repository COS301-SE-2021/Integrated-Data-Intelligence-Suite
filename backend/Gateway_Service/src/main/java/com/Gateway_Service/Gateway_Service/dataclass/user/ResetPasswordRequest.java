package com.Gateway_Service.Gateway_Service.dataclass.user;

public class ResetPasswordRequest {
    private String newPassword;
    private String email;
    private String otp;

    public ResetPasswordRequest() {

    }

    public ResetPasswordRequest(String newPassword, String email, String otp) {
        this.newPassword = newPassword;
        this.email = email;
        this.otp = otp;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
