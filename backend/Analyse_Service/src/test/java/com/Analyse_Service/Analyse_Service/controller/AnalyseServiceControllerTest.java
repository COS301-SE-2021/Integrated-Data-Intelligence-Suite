package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedArticle;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import com.Analyse_Service.Analyse_Service.response.AnalyseDataResponse;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.mock.SerializableMode;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AnalyseServiceController.class)
public class AnalyseServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyseServiceImpl service;

    @Test
    @DisplayName("When analyze check connection")
    public void analyzeRequestConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest() throws Exception {

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        dataList.add(parsedData);

        ArrayList<ParsedArticle> articleList = new ArrayList<>();
        ParsedArticle parsedArticle = new ParsedArticle();
        articleList.add(parsedArticle);

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(dataList,articleList);


        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(analyseRequest);

        AnalyseDataResponse analyseDataResponse = new AnalyseDataResponse(null,null,null,null,null,null);
        when(service.analyzeData(any(AnalyseDataRequest.class))).thenReturn(analyseDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof InvalidRequestException));

        AnalyseDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), AnalyseDataResponse.class);

        Assertions.assertNotNull(returnClass);

    }

    @Test
    @DisplayName("When analyzeRequest is Success")
    public void analyzeDataSuccessfulRequest() throws Exception {

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        parsedData.setTextMessage("MockTextMesssage");
        parsedData.setLikes(1);
        parsedData.setDate("2020/04/12");
        parsedData.setLocation("location");

        dataList.add(parsedData);

        ArrayList<ParsedArticle> articleList = new ArrayList<>();
        ParsedArticle parsedArticle = new ParsedArticle();
        parsedArticle.setContent("MockTextMesssage");
        parsedArticle.setTitle("1");
        parsedArticle.setDate("2020/04/12");
        parsedArticle.setDescription("location");
        articleList.add(parsedArticle);



        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(dataList, articleList);

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(analyseRequest);

        AnalyseDataResponse analyseDataResponse = new AnalyseDataResponse(null, null, null, null, null,null);
        when(service.analyzeData(any(AnalyseDataRequest.class))).thenReturn(analyseDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        AnalyseDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), AnalyseDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }
}
