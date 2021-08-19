package com.Visualize_Service.Visualize_Service.controller;

import com.Visualize_Service.Visualize_Service.VisualizeServiceApplication;
import com.Visualize_Service.Visualize_Service.service.VisualizeServiceImpl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisualizeServiceController.class)
public class VisualizeServiceControllerTest {
    @InjectMocks
    private VisualizeServiceController controller;

    @Mock
    private VisualizeServiceImpl service;

    private MockMvc mockMvc;

    /*@Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }*/

    @Test
    @DisplayName("When visualize is requested")
    public void visualizeRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Visualize")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
