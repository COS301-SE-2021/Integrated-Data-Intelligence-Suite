package com.Analyse_Service.Analyse_Service.controller;

import com.Analyse_Service.Analyse_Service.AnalyseServiceApplication;
import com.Analyse_Service.Analyse_Service.dataclass.AIModel;
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

    //@Autowired
    //private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("When analyze is requested")
    public void analyzeRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest() throws Exception {

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        dataList.add(parsedData);

        AnalyseDataRequest analyseRequest = new AnalyseDataRequest(dataList);


        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(analyseRequest);

        AnalyseDataResponse analyseDataResponse = new AnalyseDataResponse(null,null,null,null,null);
        when(service.analyzeData(any(AnalyseDataRequest.class))).thenReturn(analyseDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        AnalyseDataResponse someClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), AnalyseDataResponse.class);

        Assertions.assertNotNull(someClass);

        //RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Analyse/analyzeData");
        //MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //ObjectReader or = mapper.reader();
        //String requestJson=or.readValue(result.andReturn().getResponse().getContentAsString(),AnalyseDataResponse.class)


        //AnalyseDataRequest analyseRequest = Mockito.mock(AnalyseDataRequest.class);
        //analyseRequest.setDataList(analyseRequest);

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //HttpHeaders headers = new HttpHeaders();
        //headers.set("X-COM-PERSIST", "true");

        HttpEntity<AnalyseDataRequest> request = new HttpEntity<>(analyseRequest, requestHeaders);*/
        //RequestEntity<AnalyseDataRequest> req = new RequestEntity(analyseRequest, requestHeaders);

        //ResponseEntity<AnalyseDataResponse> result = this.testRestTemplate.postForEntity("/Analyse/analyzeData", request, AnalyseDataResponse.class);



        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        //AnalyseDataResponse analyseDataResponse = Mockito.mock(AnalyseDataResponse.class, withSettings().serializable(SerializableMode.ACROSS_CLASSLOADERS));


        //OngoingStubbing<AnalyseDataResponse> ongoingStubbing =
        //AnalyseDataResponse analyseDataResponse =  mockChannel
        //ongoingStubbing.thenReturn(analyseDataResponse);

        //doReturn(AnalyseDataResponse.class).when(service.analyzeData(any(AnalyseDataRequest.class)));


        /*Class response = AnalyseDataResponse.class;
        OngoingStubbing<AnalyseDataResponse> ongoingStubbing =  when(service.analyzeData(any(AnalyseDataRequest.class)));
        ongoingStubbing.thenReturn(response);*/


                //.andExpect(resulte -> Assertions.assertTrue(resulte.getResolvedException() instanceof InvalidRequestException));
                //.andExpect(result2 -> Assertions.assertThrows(InvalidRequestException.class, () -> result2.getResolvedException()))
                //.andExpect(result -> Assertions.assertSame(result.getResolvedException(),  CoreMatchers.instanceOf(InvalidRequestException.class)));
        //result2.getResolvedException(),  CoreMatchers.instanceOf(SecurityException.class
        //.andExpect(result -> Assertions.assertEquals("AnalyzeDataRequest Object is null",result.getResolvedException()));


        /*ArgumentCaptor<AnalyseDataResponse> aiModelArgumentCaptor = ArgumentCaptor.forClass(AnalyseDataResponse.class);

        AnalyseDataResponse testModel = aiModelArgumentCaptor.getValue();

        Assertions.assertNotNull(testModel);*/



        /*Assertions.assertThrows(InvalidRequestException.class,() ->mockMvc.perform(MockMvcRequestBuilders.post("/Analyse/analyzeData")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)) );*/


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
