package com.User_Service.User_Service.controller;


import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
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
    @PostMapping(value = "/changepermission")
    public @ResponseBody ManagePersmissionsResponse managePermissions(@RequestBody ManagePermissionsRequest request) throws Exception {
        //ManagePermissionsRequest request = requestEntity.getBody();
        return service.managePermissions(request);
    }

    @GetMapping(value = "/getAll",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody GetAllUsersResponse getAllUsers() throws Exception {
        //GetAllUsersRequest request = requestEntity.getBody();
        return service.getAllUsers();
    }

    @PostMapping(value = "/getUser" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody GetUserResponse getUser(@RequestBody GetUserRequest request) throws Exception{
        //GetUserRequest request = requestEntity.getBody();
        return service.getUser(request);
    }

    @PostMapping(value = "/register")
    public @ResponseBody RegisterResponse register(@RequestBody RegisterRequest request) throws Exception {
        //RegisterRequest request = requestEntity.getBody();
        return service.register(request);
    }

    @PostMapping(value = "/requestAdmin")
    public @ResponseBody RegisterAdminResponse registerAdmin(@RequestBody RegisterAdminRequest request) throws Exception {
        //RegisterRequest request = requestEntity.getBody();
        return service.requestAdmin(request);
    }

    @PostMapping(value = "/login")
    public @ResponseBody LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        //LoginRequest request = requestEntity.getBody();
        return service.login(request);
    }
}
