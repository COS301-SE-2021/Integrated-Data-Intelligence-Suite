package com.User_Service.User_Service.controller;


import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/User")
public class UserServiceController {

    @Autowired
    private UserServiceImpl service;
    /**
     * This function will allow the admin to change the permissions of a user.
     * @param requestEntity The request containing the necessary information about the user.
     * @return A class that contains if the update was successful or not.
     * @throws Exception Thrown when any exceptions are encountered
     */
    @PostMapping(value = "/changepermission")
    public ManagePersmissionsResponse managePermissions(RequestEntity<ManagePermissionsRequest> requestEntity) throws Exception {
        ManagePermissionsRequest request = requestEntity.getBody();
        return service.managePermissions(request);
    }

    @GetMapping(value = "/getAll",  produces = {MediaType.APPLICATION_JSON_VALUE})
    public GetAllUsersResponse getAllUsers(RequestEntity<GetAllUsersRequest> requestEntity) throws Exception {
        GetAllUsersRequest request = requestEntity.getBody();
        return service.getAllUsers(request);
    }

    @PostMapping(value = "/getUser" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public GetUserResponse getUser(RequestEntity<GetUserRequest> requestEntity) throws Exception{
        GetUserRequest request = requestEntity.getBody();
        return service.getUser(request);
    }

    @PostMapping(value = "/register")
    public RegisterResponse register(RequestEntity<RegisterRequest> requestEntity) throws Exception {
        RegisterRequest request = requestEntity.getBody();
        return service.register(request);
    }

    @PostMapping(value = "/login")
    public LoginResponse login(RequestEntity<LoginRequest> requestEntity) throws Exception {
        LoginRequest request = requestEntity.getBody();
        return service.login(request);
    }
}
