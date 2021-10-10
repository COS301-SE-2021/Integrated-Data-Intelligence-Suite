package com.Gateway_Service.Gateway_Service.controller;

/*import com.Analyse_Service.Analyse_Service.controller.AnalyseServiceController;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedArticle;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.AnalyseDataRequest;
import com.Analyse_Service.Analyse_Service.response.AnalyseDataResponse;
import com.Analyse_Service.Analyse_Service.service.AnalyseServiceImpl;*/
import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataRequest;
import com.Gateway_Service.Gateway_Service.dataclass.analyse.AnalyseDataResponse;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedArticle;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedData;
import com.Gateway_Service.Gateway_Service.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.aspectj.lang.annotation.Before;
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
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
@WebMvcTest(GatewayServiceController.class)
public class GatewayServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@InjectMocks
    //@MockBean
    //private GatewayServiceController controller;

    //@MockBean
    //private AnalyseServiceImpl service;


    @MockBean
    private DiscoveryClient discoveryClient;

    //@Mock
    @MockBean
    private ImportService importClient;

    @MockBean
    private ParseService parseClient;

    @MockBean
    private AnalyseService analyseClient;

    @MockBean
    private VisualizeService visualizeClient;

    @MockBean
    private UserService userClient;



    /*@Test
    @DisplayName("gateway check connection")
    public void gatewayRequestConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/main/key"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When analyzeRequest is empty")
    public void analyzeDataEmptyRequest() throws Exception {

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        dataList.add(parsedData);

        ArrayList<ParsedArticle> articleList = new ArrayList<>();
        ParsedArticle parsedArticle = new ParsedArticle();
        articleList.add(parsedArticle);

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(dataList,articleList);

        AnalyseDataResponse analyseResponse = analyseClient.analyzeData(analyseRequest);

        Assertions.assertNull(analyseResponse);

        /*ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(analyseRequest);

        AnalyseDataResponse analyseDataResponse = new AnalyseDataResponse(null,null,null,null,null);
        when( analyseClient.analyzeData(any(AnalyseDataRequest.class))).thenReturn(analyseDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof GatewayException));

        AnalyseDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), AnalyseDataResponse.class);*
    }


    /*@Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest2() throws Exception {

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

        AnalyseDataResponse analyseDataResponse = new AnalyseDataResponse(null,null,null,null,null, null);
        when( analyseClient.analyzeData(any(AnalyseDataRequest.class))).thenReturn(analyseDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof GatewayException));

        AnalyseDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), AnalyseDataResponse.class);

        Assertions.assertNotNull(returnClass);

    }*/













    /*@Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("When Gateway is requested")
    public void gatewayRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }*/
}
