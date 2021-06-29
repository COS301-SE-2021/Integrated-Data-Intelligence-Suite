package com.User_Service.User_Service.service;

import com.User_Service.User_Service.request.GetCurrentUserRequest;
import com.User_Service.User_Service.request.LoginRequest;
import com.User_Service.User_Service.request.ResetPasswordRequest;
import com.User_Service.User_Service.request.VerifyAccountRequest;
import com.User_Service.User_Service.response.*;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    public UserServiceImpl() {

    }

    public LoginResponse login(LoginRequest request) {
        return null;
    }

    public RegisterResponse register(RegisterResponse request) {
        return null;
    }

    public VerifyAccountResponse verifyAccount(VerifyAccountRequest request) {
        return null;
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        return null;
    }

    public GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request) {
        return null;
    }
}
