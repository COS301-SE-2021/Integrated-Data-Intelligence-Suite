package com.Visualize_Service.Visualize_Service.controller;

import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.VisualizeDataRequest;
import com.Visualize_Service.Visualize_Service.response.VisualizeDataResponse;
import com.Visualize_Service.Visualize_Service.service.VisualizeServiceImpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisualizeServiceController.class)
public class VisualizeServiceControllerTest {

    @MockBean
    private VisualizeServiceImpl service;

    @Autowired
    private MockMvc mockMvc;



    @Test
    @DisplayName("When import check connection")
    public void parseRequestConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Visualize/visualizeData"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When importRequest is Null")
    public void parseDataNullRequest() throws Exception {

        ArrayList<ArrayList> patternList = new ArrayList<>();
        ArrayList<ArrayList> relationshipList = new ArrayList<>();
        ArrayList<ArrayList> predictionList = new ArrayList<>();
        ArrayList<ArrayList> trendList = new ArrayList<>();
        ArrayList<String> anomalyList = new ArrayList<>();
        ArrayList<ArrayList> wordList = new ArrayList<>();

        VisualizeDataRequest visualizeDataRequest = new VisualizeDataRequest(
                patternList,
                relationshipList,
                predictionList,
                trendList,
                anomalyList,
                wordList);


        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(visualizeDataRequest);

        ArrayList<ArrayList> output = new ArrayList<>();

        VisualizeDataResponse visualizeDataResponse = new VisualizeDataResponse(output);
        when(service.visualizeData(any(VisualizeDataRequest.class))).thenReturn(visualizeDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Visualize/visualizeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof InvalidRequestException));

        VisualizeDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), VisualizeDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }


    @Test
    @DisplayName("When importRequest is Success")
    public void parseDataSuccessfulRequest() throws Exception {

        ArrayList<ArrayList> patternList = new ArrayList<>();
        ArrayList<ArrayList> relationshipList = new ArrayList<>();
        ArrayList<ArrayList> predictionList = new ArrayList<>();
        ArrayList<ArrayList> trendList = new ArrayList<>();
        ArrayList<String> anomalyList = new ArrayList<>();
        ArrayList<ArrayList> wordList = new ArrayList<>();

        VisualizeDataRequest visualizeDataRequest = new VisualizeDataRequest(
                patternList,
                relationshipList,
                predictionList,
                trendList,
                anomalyList,
                wordList);


        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(visualizeDataRequest);

        ArrayList<ArrayList> output = new ArrayList<>();

        VisualizeDataResponse visualizeDataResponse = new VisualizeDataResponse(output);
        when(service.visualizeData(any(VisualizeDataRequest.class))).thenReturn(visualizeDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Visualize/visualizeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        VisualizeDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), VisualizeDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }



}
