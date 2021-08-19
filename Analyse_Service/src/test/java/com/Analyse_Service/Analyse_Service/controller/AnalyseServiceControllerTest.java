package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.AnalyseServiceApplication;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import com.Analyse_Service.Analyse_Service.response.AnalyseDataResponse;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AnalyseServiceController.class)
public class AnalyseServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyseServiceImpl service;

    //@Autowired
    //private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("When analyze is requested")
    public void analyzeRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Analyse/analyzeData");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        mockMvc.perform( requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest() throws Exception {

        //RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Analyse/analyzeData");
        //MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(null);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //HttpHeaders headers = new HttpHeaders();
        //headers.set("X-COM-PERSIST", "true");

        HttpEntity<AnalyseDataRequest> request = new HttpEntity<>(analyseRequest, requestHeaders);

        //ResponseEntity<AnalyseDataResponse> result = this.testRestTemplate.postForEntity("/Analyse/analyzeData", request, AnalyseDataResponse.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(analyseRequest);


        mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertEquals("AnalyzeDataRequest Object is null",result.getResolvedException()));


        //Assertions.assertEquals(200,result.getStatusCodeValue());
        //Assertions.assertNotNull(result.getBody());

        /*ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );*/

        //Assertions.assertThrows()

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        AnalyseDataRequest analyseRequest = null;
        HttpEntity<AnalyseDataRequest> instance =new HttpEntity<>(analyseRequest,requestHeaders);

        Gson gson = new Gson();
        System.out.println(gson.toJson(instance));
        String json = gson.toJson(instance);
        System.out.println(json);*/

        /*mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidRequestException));
                //.andExpect(result -> Assertions.assertEquals("resource not found", result.getResolvedException().getMessage()));*/
    }
}
