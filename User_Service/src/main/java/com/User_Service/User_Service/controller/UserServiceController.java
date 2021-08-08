package com.User_Service.User_Service.controller;


import com.User_Service.User_Service.request.GetAllUsersRequest;
import com.User_Service.User_Service.request.ManagePermissionsRequest;
import com.User_Service.User_Service.request.RegisterRequest;
import com.User_Service.User_Service.response.GetAllUsersResponse;
import com.User_Service.User_Service.response.ManagePersmissionsResponse;
import com.User_Service.User_Service.response.RegisterResponse;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
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

    @PostMapping(value = "/allusers")
    public GetAllUsersResponse getAllUsers(RequestEntity<GetAllUsersRequest> requestEntity) throws Exception {
        GetAllUsersRequest request = requestEntity.getBody();
        return service.getAllUsers(request);
    }

    @PostMapping(value = "/register")
    public RegisterResponse register(RequestEntity<RegisterRequest> requestEntity) throws Exception {
        RegisterRequest request = requestEntity.getBody();
        return service.register(request);
    }
}
