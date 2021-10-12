package com.User_Service.User_Service.controller;


import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/User")
public class UserServiceController {

    @Autowired
    private UserServiceImpl service;
    /**
     * This function will allow the admin to change the permissions of a user.
     * @param request The request containing the necessary information about the user.
     * @return A class that contains if the update was successful or not.
     * @throws Exception Thrown when any exceptions are encountered
     */
    @PostMapping(value = "/changeUser")
    public @ResponseBody ResponseEntity<?> changeUser(@RequestBody ChangeUserRequest request) throws Exception {

        ChangeUserResponse changeUserResponse = service.changeUser(request);
        return new ResponseEntity<>(changeUserResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * This function will allow the gateway to connect to the user service for getting all users.
     * @return A class that contains if the update was successful or not.
     * @throws Exception Thrown when any exceptions are encountered
     */
    @GetMapping(value = "/getAll",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> getAllUsers() throws Exception {

        GetAllUsersResponse getAllUsersResponse = service.getAllUsers();
        return new ResponseEntity<>(getAllUsersResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/getUser" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> getUser(@RequestBody GetUserRequest request) throws Exception{

        GetUserResponse getUserResponse = service.getUser(request);
        return new ResponseEntity<>(getUserResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody RegisterRequest request) throws Exception {

        RegisterResponse registerResponse = service.register(request);
        return new ResponseEntity<>(registerResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/requestAdmin")
    public @ResponseBody ResponseEntity<?> registerAdmin(@RequestBody RequestAdminRequest request) throws Exception {

        RequestAdminResponse requestAdminResponse = service.requestAdmin(request);
        return new ResponseEntity<>(requestAdminResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {

        LoginResponse loginResponse= service.login(request);
        return new ResponseEntity<>(loginResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/getCurrentUser", produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<?> getCurrentUser(@RequestBody GetCurrentUserRequest request) throws Exception {

        GetCurrentUserResponse getCurrentUserResponse =  service.getCurrentUser(request);
        return new ResponseEntity<>(getCurrentUserResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/verifyAccount")
    public @ResponseBody ResponseEntity<?> verifyAccount(@RequestBody VerifyAccountRequest request) throws Exception {

        VerifyAccountResponse verifyAccountResponse = service.verifyAccount(request);
        return new ResponseEntity<>(verifyAccountResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/resendCode")
    public @ResponseBody ResponseEntity<?> resendCode(@RequestBody ResendCodeRequest request) throws Exception {

        ResendCodeResponse resendCodeResponse = service.resendCode(request);
        return new ResponseEntity<>(resendCodeResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/sendOTP")
    public @ResponseBody ResponseEntity<?> sendOTP(@RequestBody ResendCodeRequest request) throws Exception {

        ResendCodeResponse resendCodeResponse = service.sendOTP(request);
        return new ResponseEntity<>(resendCodeResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/resetPassword")
    public @ResponseBody ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws Exception {

        ResetPasswordResponse resetPasswordResponse = service.resetPassword(request);
        return new ResponseEntity<>(resetPasswordResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/updateProfile")
    public @ResponseBody ResponseEntity<?> resendCode(@RequestBody UpdateProfileRequest request) throws Exception {

        UpdateProfileResponse updateProfileResponse = service.updateProfile(request);
        return new ResponseEntity<>(updateProfileResponse, new HttpHeaders(), HttpStatus.OK);
    }




    @PostMapping(value = "/addReport")
    public @ResponseBody ResponseEntity<?> addReport(@RequestBody ReportRequest request) throws Exception {

        ReportResponse reportResponse = service.addReport(request);
        return new ResponseEntity<>(reportResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/removeReport")
    public @ResponseBody ResponseEntity<?> removeReport(@RequestBody ReportRequest request) throws Exception {

        ReportResponse reportResponse = service.removeReport(request);;
        return new ResponseEntity<>(reportResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/getReports/{id}")
    public @ResponseBody ResponseEntity<?> getReports(@PathVariable String id) throws Exception {

        GetUserReportsResponse getUserReportsResponse = service.getReports(new GetUserReportsRequest(id));
        return new ResponseEntity<>(getUserReportsResponse, new HttpHeaders(), HttpStatus.OK);
    }




    @GetMapping(value = "/getModels/{id}")
    public @ResponseBody ResponseEntity<?> getModels(@PathVariable String id) throws Exception {

        GetModelsResponse getModelsResponse = service.getModels(new GetModelsRequest(id));
        return new ResponseEntity<>(getModelsResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/addModel")
    public @ResponseBody ResponseEntity<?> addModel(@RequestBody ModelRequest request) throws Exception {

        ModelResponse modelResponse = service.addModel(request);
        return new ResponseEntity<>(modelResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/removeModel")
    public @ResponseBody ResponseEntity<?> removeModel(@RequestBody ModelRequest request) throws Exception {

        ModelResponse modelResponse = service.removeModel(request);
        return new ResponseEntity<>(modelResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/selectModel")
    public @ResponseBody ResponseEntity<?> selectModel(@RequestBody ModelRequest request) throws Exception {

        ModelResponse modelResponse = service.selectModel(request);
        return new ResponseEntity<>(modelResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/deselectModel")
    public @ResponseBody ResponseEntity<?> deselectModel(@RequestBody ModelRequest request) throws Exception {

        ModelResponse modelResponse = service.deselectModel(request);
        return new ResponseEntity<>(modelResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
