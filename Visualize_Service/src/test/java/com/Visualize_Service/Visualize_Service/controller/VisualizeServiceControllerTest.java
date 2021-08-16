package com.Visualize_Service.Visualize_Service.controller;

import com.Visualize_Service.Visualize_Service.VisualizeServiceApplication;
import com.Visualize_Service.Visualize_Service.service.VisualizeServiceImpl;

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
@SpringBootTest(classes = VisualizeServiceApplication.class)
public class VisualizeServiceControllerTest {
    @InjectMocks
    private VisualizeServiceController controller;

    @Mock
    private VisualizeServiceImpl service;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When visualize is requested")
    public void visualizeRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Visualize")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
