package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.AnalyseServiceApplication;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AnalyseServiceController.class)
public class AnalyseServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When analyze is requested")
    public void analyzeRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Analyse")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest() throws Exception {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        AnalyseDataRequest analyseRequest = null;
        HttpEntity<AnalyseDataRequest> instance =new HttpEntity<>(analyseRequest,requestHeaders);

        Gson gson = new Gson();
        System.out.println(gson.toJson(instance));
        String json = gson.toJson(instance);
        System.out.println(json);

        mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidRequestException));
                //.andExpect(result -> Assertions.assertEquals("resource not found", result.getResolvedException().getMessage()));
    }
}
