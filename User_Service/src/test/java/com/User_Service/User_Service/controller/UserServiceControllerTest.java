package com.User_Service.User_Service.controller;

import com.User_Service.User_Service.UserServiceApplication;
import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.request.GetUserRequest;
import com.User_Service.User_Service.request.LoginRequest;
import com.User_Service.User_Service.request.ManagePermissionsRequest;
import com.User_Service.User_Service.request.RegisterRequest;
import com.User_Service.User_Service.response.GetAllUsersResponse;
import com.User_Service.User_Service.rri.Permission;
import com.User_Service.User_Service.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    @DisplayName("When_user_managePermissions_is_Requested")
    public void userManagePermissions() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/User/changepermission")
                .content(asJsonString(new ManagePermissionsRequest("randomUsername", Permission.IMPORTING)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
