package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * This functions sends a request to the user service using REST to change the permission
     * of a specific user.
     * @param request This class contains the required information of a specific user to
     *                change the permission of that user.
     * @return This class will contain the information whether or not the request was successfull
     *         or not.
     */
    public ManagePermissionsResponse managePermissions(ManagePermissionsRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ManagePermissionsRequest> requestEntity = new HttpEntity<ManagePermissionsRequest>(request, requestHeaders);
        ResponseEntity<ManagePermissionsResponse> responseEntity = restTemplate.exchange("http://User-Service/User/changepermission", HttpMethod.POST, requestEntity, ManagePermissionsResponse.class);
        return responseEntity.getBody();
    }

    /**
     * This function sends a request to the user service to get all users saved on the database.
     * @param request ***
     * @return This class contains a list of users saved on the system.
     */
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetAllUsersRequest> requestEntity = new HttpEntity<GetAllUsersRequest>(request, requestHeaders);
        ResponseEntity<GetAllUsersResponse> responseEntity = restTemplate.exchange("http://User-Service/User/getAll", HttpMethod.GET, requestEntity, GetAllUsersResponse.class);
        return responseEntity.getBody();
    }

    /**
     * This function is used to connect to the user service to allow the user to register
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param request This class contains all the information of the user to be saved.
     * @return This class contains the information if the saving of the user was successful.
     */
    public RegisterResponse register(RegisterRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<RegisterResponse> responseEntity = restTemplate.exchange("http://User-Service/User/register", HttpMethod.POST, requestEntity, RegisterResponse.class);
        return responseEntity.getBody();
    }
    /**
     * This function is used to connect to the user service to allow the user to login
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param request This class contains the email and password of the user to login.
     * @return This class contains the information if the logging process was successful.
     */
    public LoginResponse login(LoginRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<LoginResponse> responseEntity = restTemplate.exchange("http://User-Service/User/login", HttpMethod.POST, requestEntity, LoginResponse.class);
        return responseEntity.getBody();
    }
}


