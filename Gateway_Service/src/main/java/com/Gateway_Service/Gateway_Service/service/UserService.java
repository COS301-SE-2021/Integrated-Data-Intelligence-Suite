package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
     * @param userRequest This class contains the required information of a specific user to
     *                change the permission of that user.
     * @return This class will contain the information whether or not the request was successfull
     *         or not.
     */
    public ChangeUserResponse managePermissions(ChangeUserRequest userRequest) {
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChangeUserRequest> requestEntity = new HttpEntity<ChangeUserRequest>(request, requestHeaders);
        ResponseEntity<ChangeUserResponse> responseEntity = restTemplate.exchange("http://User-Service/User/changepermission", HttpMethod.POST, requestEntity, ChangeUserResponse.class);
        return responseEntity.getBody();*/

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ChangeUserResponse userResponse = restTemplate.postForObject("http://User-Service/User/changepermission", request, ChangeUserResponse.class);

        return userResponse;
    }

    /**
     * This function sends a request to the user service to get all users saved on the database.
     * @return This class contains a list of users saved on the system.
     */
    public GetAllUsersResponse getAllUsers() {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        ResponseEntity<GetAllUsersResponse> responseEntity = restTemplate.exchange("http://User-Service/User/getAll", HttpMethod.GET, null, GetAllUsersResponse.class);

        GetAllUsersResponse getAllUsersResponse = responseEntity.getBody();

        return getAllUsersResponse;
    }

    /**
     * This function is used to connect to the user service to allow the user to register
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains all the information of the user to be saved.
     * @return This class contains the information if the saving of the user was successful.
     */
    public RegisterResponse register(RegisterRequest userRequest) {
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<RegisterResponse> responseEntity = restTemplate.exchange("http://User-Service/User/register", HttpMethod.POST, requestEntity, RegisterResponse.class);
        return responseEntity.getBody();*/

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RegisterResponse userResponse = restTemplate.postForObject("http://User-Service/User/register", request, RegisterResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service to allow the user to register
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains all the information of the user to be saved.
     * @return This class contains the information if the saving of the user was successful.
     */
    /*
    public RegisterAdminResponse requestAdmin(RegisterAdminRequest userRequest) {
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<RegisterResponse> responseEntity = restTemplate.exchange("http://User-Service/User/register", HttpMethod.POST, requestEntity, RegisterResponse.class);
        return responseEntity.getBody();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RegisterAdminResponse userResponse = restTemplate.postForObject("http://User-Service/User/requestAdmin", request, RegisterAdminResponse.class);

        return userResponse;
    }
    */

    public VerifyAccountResponse verifyAccount(VerifyAccountRequest userRequest) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        VerifyAccountResponse userResponse = restTemplate.postForObject("http://User-Service/User/verifyAccount", request, VerifyAccountResponse.class);

        return userResponse;
    }

    public GetUserResponse getUser(GetUserRequest userRequest){
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetUserRequest> requestEntity = new HttpEntity<GetUserRequest>(request, requestHeaders);
        ResponseEntity<GetUserResponse> responseEntity = restTemplate.exchange("http://User-Service/User/getUser", HttpMethod.POST, requestEntity, GetUserResponse.class);
        return responseEntity.getBody();*/
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        GetUserResponse userResponse = restTemplate.postForObject("http://User-Service/User/getUser", request, GetUserResponse.class);

        return userResponse;
    }
    /**
     * This function is used to connect to the user service to allow the user to login
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the email and password of the user to login.
     * @return This class contains the information if the logging process was successful.
     */
    public LoginResponse login(LoginRequest userRequest) {
        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<LoginResponse> responseEntity = restTemplate.exchange("http://User-Service/User/login", HttpMethod.POST, requestEntity, LoginResponse.class);
        return responseEntity.getBody();*/
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(userRequest),requestHeaders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LoginResponse userResponse = restTemplate.postForObject("http://User-Service/User/login", request, LoginResponse.class);

        return userResponse;
    }
}


