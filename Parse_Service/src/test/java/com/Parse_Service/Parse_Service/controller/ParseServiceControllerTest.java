package com.Parse_Service.Parse_Service.controller;

import com.Parse_Service.Parse_Service.ParseServiceApplication;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;

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
@SpringBootTest(classes = ParseServiceApplication.class)
public class ParseServiceControllerTest {
    @InjectMocks
    private ParseServiceController controller;

    @Mock
    private ParseServiceImpl service;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When parse is requested")
    public void parseRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Parse")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
