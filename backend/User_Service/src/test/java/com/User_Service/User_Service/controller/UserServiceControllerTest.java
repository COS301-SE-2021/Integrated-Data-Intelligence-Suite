package controller;

import com.User_Service.User_Service.controller.UserServiceController;
import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.request.ChangeUserRequest;
import com.User_Service.User_Service.request.GetUserRequest;
import com.User_Service.User_Service.request.LoginRequest;
import com.User_Service.User_Service.request.RegisterRequest;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import com.User_Service.User_Service.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserServiceController.class)
public class UserServiceControllerTest {


    @MockBean
    private UserServiceImpl service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("When_getAll_is_requested")
    public void userGetAllRequest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/User/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When_getAll_is_requested_valid_data")
    public void userGetAllRequestValidData() throws Exception {

        List<User> users = new ArrayList<>();
        GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse("message", true, users);

        when(service.getAllUsers()).thenReturn(getAllUsersResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/User/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase cha

        GetAllUsersResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), GetAllUsersResponse.class);

        Assertions.assertNotNull(returnClass);
    }

    @Test
    @DisplayName("When_user_login_is_requested")
    public void userLoginRequest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/User/login")
                .content(asJsonString(new LoginRequest("shreymandalia@gmail.com", "pass")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When_user_login_is_requested_valid_data")
    public void userLoginRequestValidData() throws Exception {

        LoginRequest loginRequest = new LoginRequest();

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginRequest);

        LoginResponse loginResponse = new LoginResponse();
        when(service.login(any(LoginRequest.class))).thenReturn(loginResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/User/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        LoginResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), LoginResponse.class);

        Assertions.assertNotNull(returnClass);
    }

    @Test
    @DisplayName("When_user_register_is_requested")
    public void userRegisterRequest() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/User/register")
                .content(asJsonString(new RegisterRequest("randomUsername", "firstname", "lastname", "password", "randomEmail")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When_user_register_is_requested_valid_data")
    public void userRegisterRequestValidData() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("username",
                "firstName",
                "lastName",
                "password",
                    "email");

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(registerRequest);

        RegisterResponse registerResponse = new RegisterResponse(true, "message");
        when(service.register(any(RegisterRequest.class))).thenReturn(registerResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/User/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        RegisterResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), RegisterResponse.class);

        Assertions.assertNotNull(returnClass);
    }

    @Test
    @DisplayName("When_User_getUser_Is_Requested")
    public void userGetUserRequest() throws Exception {
        UUID id = UUID.fromString("0b4e8936-bd7e-4373-b097-ce227b9f4072");
        mockMvc.perform( MockMvcRequestBuilders
                .post("/User/getUser")
                .content(asJsonString(new GetUserRequest(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When_User_getUser_Is_Requested_valid_data")
    public void userGetUserRequestValidData() throws Exception {
        UUID id = UUID.fromString("0b4e8936-bd7e-4373-b097-ce227b9f4072");
        GetUserRequest getUserRequest = new GetUserRequest(id);

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getUserRequest);

        List<User> user = new ArrayList<>();
        GetUserResponse getUserResponse = new GetUserResponse("message", true, user);
        when(service.getUser(any(GetUserRequest.class))).thenReturn(getUserResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/User/getUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        GetUserResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), GetUserResponse.class);

        Assertions.assertNotNull(returnClass);
    }

    @Test
    @DisplayName("When_user_managePermissions_is_Requested")
    public void userManagePermissions() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/User/changeUser")
                .content(asJsonString(new ChangeUserRequest("randomUsername", false, Permission.IMPORTING)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When_user_managePermissions_is_Requested_valid_data")
    public void userManagePermissionsValidData() throws Exception {
        ChangeUserRequest changeUserRequest = new ChangeUserRequest("userName", false, Permission.VIEWING);

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(changeUserRequest);

        ChangeUserResponse changeUserResponse = new ChangeUserResponse("message", true);
        when(service.changeUser(any(ChangeUserRequest.class))).thenReturn(changeUserResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/User/changeUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ChangeUserResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ChangeUserResponse.class);

        Assertions.assertNotNull(returnClass);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
