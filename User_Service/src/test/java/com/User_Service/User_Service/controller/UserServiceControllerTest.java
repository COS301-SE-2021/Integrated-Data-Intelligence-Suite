package com.User_Service.User_Service.controller;

import com.User_Service.User_Service.UserServiceApplication;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceControllerTest {
    @InjectMocks
    private UserServiceController controller;

    @Mock
    private UserServiceImpl service;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When user is requested")
    public void userRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/User")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
