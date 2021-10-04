package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserRequest;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserResponse;
import com.Gateway_Service.Gateway_Service.dataclass.user.*;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
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
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

    public VerifyAccountResponse verifyAccount(VerifyAccountRequest userRequest) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

    public ResendCodeResponse resendCode(ResendCodeRequest userRequest) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ResendCodeResponse userResponse = restTemplate.postForObject("http://User-Service/User/resendCode", request, ResendCodeResponse.class);

        return userResponse;
    }

    public ResendCodeResponse sendOTP(ResendCodeRequest userRequest) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ResendCodeResponse userResponse = restTemplate.postForObject("http://User-Service/User/sendOTP", request, ResendCodeResponse.class);

        return userResponse;
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest userRequest) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

        System.out.println(userRequest.getNewPassword() + " " + userRequest.getEmail() + " " + userRequest.getOtp());

        ResetPasswordResponse userResponse = restTemplate.postForObject("http://User-Service/User/resetPassword", request, ResetPasswordResponse.class);

        return userResponse;
    }

    public GetUserResponse getUser(GetUserRequest userRequest){

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public UpdateProfileResponse updateProfile(UpdateProfileRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        UpdateProfileResponse userResponse = restTemplate.postForObject("http://User-Service/User/updateProfile", request, UpdateProfileResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public GetUserReportsResponse getUserReports(GetUserReportsRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        GetUserReportsResponse userResponse = restTemplate.getForObject("http://User-Service/User/getReports/" + userRequest.getId(), GetUserReportsResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ReportResponse addReportForUser(ReportRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ReportResponse userResponse = restTemplate.postForObject("http://User-Service/User/addReport", request, ReportResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ReportResponse removeReportForUser(ReportRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ReportResponse userResponse = restTemplate.postForObject("http://User-Service/User/removeReport", request, ReportResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public GetModelsResponse getUserModels(GetModelsRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        GetModelsResponse userResponse = restTemplate.getForObject("http://User-Service/User/getModels/" + userRequest.getUserID(), GetModelsResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse addModelForUser(ModelRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/addModel", request, ModelResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse removeModelForUser(ModelRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/removeModel", request, ModelResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse selectModel(ModelRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/selectModel", request, ModelResponse.class);

        return userResponse;
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse deselectModel(ModelRequest userRequest) {

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
        ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/deselectModel", request, ModelResponse.class);

        return userResponse;
    }
}


