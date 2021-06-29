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

    /**
     * This function logs the user in.
     * @param request This class contains the user information for login.
     * @return This returns a response contains the exit code**
     */
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    /**
     * This function registers the user to the platform.
     * @param request This class contains the information to store the user within the system.
     * @return This returns a response containing the exit code**
     */
    public RegisterResponse register(RegisterResponse request) {
        return null;
    }

    /**
     * This function verifies the user's authenticity.
     * @param request This class contains the information of the user.
     * @return The return class returns if the verification process was successful**
     */
    public VerifyAccountResponse verifyAccount(VerifyAccountRequest request) {
        return null;
    }

    /**
     * This function will allow the user to reset their password and store the new password
     * in the database.
     * @param request This class will contain the new password of the user.
     * @return This class will contain if the password reset process was successful.
     */
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        return null;
    }

    /**
     * This function will return the current user logged onto the system.
     * @param request This is the request for the use case.
     * @return This class will contain the current user logged on.
     */
    public GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request) {
        return null;
    }
}
