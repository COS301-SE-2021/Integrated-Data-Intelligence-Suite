package com.User_Service.User_Service.controller;

import com.User_Service.User_Service.UserServiceApplication;
import com.User_Service.User_Service.request.GetUserRequest;
import com.User_Service.User_Service.request.LoginRequest;
import com.User_Service.User_Service.request.ManagePermissionsRequest;
import com.User_Service.User_Service.request.RegisterRequest;
import com.User_Service.User_Service.rri.Permission;
import com.User_Service.User_Service.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;


@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceControllerTest {
    @Autowired
    private UserServiceController controller;

    @Autowired
    private UserServiceImpl service;

    private MockMvc mockMvc;

    private String randomUsername;

    private String randomEmail;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        int max = 100;
        int min = 1;
        int range = max - min + 1;
        int randomNum = (int)(Math.random() * range) + min;
        randomUsername = "testUser" + randomNum;
        randomEmail = "testEmail" + randomNum + "@test.com";
    }

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
                .content(asJsonString(new RegisterRequest(randomUsername, "firstname", "lastname", "password", randomEmail)))
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
                .content(asJsonString(new ManagePermissionsRequest(randomUsername, Permission.IMPORTING)))
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
