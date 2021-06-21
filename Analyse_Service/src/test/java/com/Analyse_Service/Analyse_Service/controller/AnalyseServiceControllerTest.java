package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.AnalyseServiceApplication;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyseServiceApplication.class)
public class AnalyseServiceControllerTest {

    @InjectMocks
    private AnalyseServiceController controller;



    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Analyse")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

 
}
