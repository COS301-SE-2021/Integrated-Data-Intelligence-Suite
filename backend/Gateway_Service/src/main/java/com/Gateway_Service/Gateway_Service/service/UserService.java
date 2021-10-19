package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserRequest;
import com.Gateway_Service.Gateway_Service.dataclass.user.GetUserResponse;
import com.Gateway_Service.Gateway_Service.dataclass.user.*;
import com.Gateway_Service.Gateway_Service.exception.AnalyserException;
import com.Gateway_Service.Gateway_Service.exception.UserException;
import com.Gateway_Service.Gateway_Service.rri.RestTemplateErrorHandler;
import com.Gateway_Service.Gateway_Service.rri.ServiceErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
    public ChangeUserResponse managePermissions(ChangeUserRequest userRequest) throws UserException {


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
        //ChangeUserResponse userResponse = restTemplate.postForObject("http://User-Service/User/changepermission", request, ChangeUserResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://user-service/User/changepermission",HttpMethod.POST,request,new ParameterizedTypeReference<ChangeUserResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ChangeUserResponse.class) {
            userResponse = restTemplate.exchange("http://User-Service/User/changepermission",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ChangeUserResponse) userResponse.getBody();
    }

    /**
     * This function sends a request to the user service to get all users saved on the database.
     * @return This class contains a list of users saved on the system.
     */
    public GetAllUsersResponse getAllUsers() throws UserException {


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true);

        //ResponseEntity<GetAllUsersResponse> responseEntity = restTemplate.exchange("http://User-Service/User/getAll", HttpMethod.GET, null, GetAllUsersResponse.class);
        //GetAllUsersResponse getAllUsersResponse = responseEntity.getBody();


        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/getAll",HttpMethod.GET,null,new ParameterizedTypeReference<GetAllUsersResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != GetAllUsersResponse.class) {
            userResponse = restTemplate.exchange("http://User-Service/User/getAll",HttpMethod.GET,null,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }

        return (GetAllUsersResponse) userResponse.getBody();

    }

    /**
     * This function is used to connect to the user service to allow the user to register
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains all the information of the user to be saved.
     * @return This class contains the information if the saving of the user was successful.
     */
    public RegisterResponse register(RegisterRequest userRequest) throws UserException {


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
        //RegisterResponse userResponse = restTemplate.postForObject("http://User-Service/User/register", request, RegisterResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/register",HttpMethod.POST,request,new ParameterizedTypeReference<RegisterResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != RegisterResponse.class) {
            userResponse = restTemplate.exchange("http://User-Service/User/register",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (RegisterResponse) userResponse.getBody();
    }

    public VerifyAccountResponse verifyAccount(VerifyAccountRequest userRequest) throws UserException {

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
        //VerifyAccountResponse userResponse = restTemplate.postForObject("http://User-Service/User/verifyAccount", request, VerifyAccountResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/verifyAccount",HttpMethod.POST,request,new ParameterizedTypeReference<VerifyAccountResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != VerifyAccountResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/verifyAccount",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (VerifyAccountResponse) userResponse.getBody();
    }

    public ResendCodeResponse resendCode(ResendCodeRequest userRequest) throws UserException {

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
        //ResendCodeResponse userResponse = restTemplate.postForObject("http://User-Service/User/resendCode", request, ResendCodeResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/resendCode",HttpMethod.POST,request,new ParameterizedTypeReference<ResendCodeResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ResendCodeResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/resendCode",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ResendCodeResponse) userResponse.getBody();
    }

    public ResendCodeResponse sendOTP(ResendCodeRequest userRequest) throws UserException {

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
        //ResendCodeResponse userResponse = restTemplate.postForObject("http://User-Service/User/sendOTP", request, ResendCodeResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/sendOTP",HttpMethod.POST,request,new ParameterizedTypeReference<ResendCodeResponse>() {});



        if(userResponse != null && userResponse.getBody().getClass() != ResendCodeResponse.class) {
            userResponse = restTemplate.exchange("http://User-Service/User/sendOTP",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ResendCodeResponse) userResponse.getBody();
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest userRequest) throws UserException {

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

        //ResetPasswordResponse userResponse = restTemplate.postForObject("http://User-Service/User/resetPassword", request, ResetPasswordResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/resetPassword",HttpMethod.POST,request,new ParameterizedTypeReference<ResetPasswordResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ResetPasswordResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/resetPassword",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ResetPasswordResponse) userResponse.getBody();
    }

    public GetUserResponse getUser(GetUserRequest userRequest) throws UserException {

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
        //GetUserResponse userResponse = restTemplate.postForObject("http://User-Service/User/getUser", request, GetUserResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/getUser",HttpMethod.POST,request,new ParameterizedTypeReference<GetUserResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != GetUserResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/getUser",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (GetUserResponse) userResponse.getBody();
    }
    /**
     * This function is used to connect to the user service to allow the user to login
     * to the system. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the email and password of the user to login.
     * @return This class contains the information if the logging process was successful.
     */
    public LoginResponse login(LoginRequest userRequest) throws UserException {

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
        //LoginResponse userResponse = restTemplate.postForObject("http://User-Service/User/login", request, LoginResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/login",HttpMethod.POST,request,new ParameterizedTypeReference<LoginResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != LoginResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/login",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (LoginResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public UpdateProfileResponse updateProfile(UpdateProfileRequest userRequest) throws UserException {

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
        //UpdateProfileResponse userResponse = restTemplate.postForObject("http://User-Service/User/updateProfile", request, UpdateProfileResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/updateProfile",HttpMethod.POST,request,new ParameterizedTypeReference<UpdateProfileResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != UpdateProfileResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/updateProfile",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (UpdateProfileResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public GetUserReportsResponse getUserReports(GetUserReportsRequest userRequest) throws UserException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //GetUserReportsResponse userResponse = restTemplate.getForObject("http://User-Service/User/getReports/" + userRequest.getId(), GetUserReportsResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/getReports/" + userRequest.getId(),HttpMethod.GET, null,new ParameterizedTypeReference<GetUserReportsResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != GetUserReportsResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/getReports/" + userRequest.getId(),HttpMethod.GET, null,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (GetUserReportsResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ReportResponse addReportForUser(ReportRequest userRequest) throws UserException {

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
        //ReportResponse userResponse = restTemplate.postForObject("http://User-Service/User/addReport", request, ReportResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/addReport",HttpMethod.POST,request,new ParameterizedTypeReference<ReportResponse>() {});

        if(userResponse != null && userResponse.getBody().getClass() != ReportResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/addReport",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ReportResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ReportResponse removeReportForUser(ReportRequest userRequest) throws UserException {

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
        //ReportResponse userResponse = restTemplate.postForObject("http://User-Service/User/removeReport", request, ReportResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/removeReport",HttpMethod.POST,request,new ParameterizedTypeReference<ReportResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ReportResponse.class) {
            userResponse = restTemplate.exchange("http://User-Service/User/removeReport",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ReportResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service to allow the user to change
     * their account detials. It sends a request to the user controller and send the request
     * class to user service.
     * @param userRequest This class contains the new details of the user to change their current details.
     * @return This class contains the information if the process of changing their account details
     * was successful or not.
     */
    public GetModelsResponse getUserModels(GetModelsRequest userRequest) throws UserException {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //GetModelsResponse userResponse = restTemplate.getForObject("http://User-Service/User/getModels/{id}" + userRequest.getUserID(), GetModelsResponse.class);


        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/getModels/" + userRequest.getUserID(),HttpMethod.GET, null,new ParameterizedTypeReference<GetModelsResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != GetModelsResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/getModels/" + userRequest.getUserID(),HttpMethod.GET, null,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (GetModelsResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse addModelForUser(ModelRequest userRequest) throws UserException {

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
        //ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/addModel", request, ModelResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/addModel",HttpMethod.POST,request,new ParameterizedTypeReference<ModelResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ModelResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/addModel",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ModelResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse removeModelForUser(ModelRequest userRequest) throws UserException {

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
        //ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/removeModel", request, ModelResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/removeModel",HttpMethod.POST,request,new ParameterizedTypeReference<ModelResponse>() {});

        if(userResponse != null && userResponse.getBody().getClass() != ModelResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/removeModel",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ModelResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse selectModel(ModelRequest userRequest) throws UserException {

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
        //ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/selectModel", request, ModelResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/selectModel",HttpMethod.POST,request,new ParameterizedTypeReference<ModelResponse>() {});

        if(userResponse != null && userResponse.getBody().getClass() != ModelResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/selectModel",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }

        return (ModelResponse) userResponse.getBody();
    }

    /**
     * This function is used to connect to the user service
     * @param userRequest This class contains the new reports.
     * @return This class contains the information if the request was successful or not.
     */
    public ModelResponse deselectModel(ModelRequest userRequest) throws UserException {

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
        //ModelResponse userResponse = restTemplate.postForObject("http://User-Service/User/deselectModel", request, ModelResponse.class);

        ResponseEntity<?> userResponse = null;
        userResponse = restTemplate.exchange("http://User-Service/User/deselectModel",HttpMethod.POST,request,new ParameterizedTypeReference<ModelResponse>() {});


        if(userResponse != null && userResponse.getBody().getClass() != ModelResponse.class) {

            userResponse = restTemplate.exchange("http://User-Service/User/deselectModel",HttpMethod.POST,request,new ParameterizedTypeReference<ServiceErrorResponse>() {});

            ServiceErrorResponse serviceErrorResponse = (ServiceErrorResponse) userResponse.getBody();
            if(serviceErrorResponse.getErrors() != null) {
                String errors = serviceErrorResponse.getErrors().get(0);
                for(int i=1; i < serviceErrorResponse.getErrors().size(); i++){
                    errors = "; " + errors;
                }

                throw new UserException(errors);
            }
        }


        return (ModelResponse) userResponse.getBody();
    }
}


