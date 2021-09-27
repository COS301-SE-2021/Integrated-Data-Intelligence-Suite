package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
@WebMvcTest(ImportServiceController.class)
public class ImportServiceControllerTest {

    @MockBean
    private ImportServiceImpl service;

    @Autowired
    private MockMvc mockMvc;


    /*@Test
    @DisplayName("When import is requested")
    public void importRequest() throws Exception {

    }

    @Test
    @DisplayName("When dated data is requested")
    public void  datedDataRequest() throws Exception {

    }*/

    @Test
    @DisplayName("When import check connection")
    public void importRequestConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Import/importData"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("When importRequest is Null")
    public void importDataNullRequest() throws Exception {

        ImportDataRequest importDataRequest = new ImportDataRequest("testKey",50);

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(importDataRequest);

        ArrayList<ImportedData> importedData = new ArrayList<>();
        ImportedData importedData1 = new ImportedData();
        importedData.add(importedData1);

        ImportDataResponse importDataResponse = new ImportDataResponse(true, "Successfully fetched data", importedData);
        when(service.importData(any(ImportDataRequest.class))).thenReturn(importDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Import/importData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof InvalidImporterRequestException));

        ImportDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ImportDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }


    @Test
    @DisplayName("When importRequest is Success")
    public void importDataSuccessfulRequest() throws Exception {

        ImportDataRequest importDataRequest = new ImportDataRequest("testKey",50);

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(importDataRequest);

        ArrayList<ImportedData> importedData = new ArrayList<>();
        ImportedData importedData1 = new ImportedData();
        importedData.add(importedData1);

        ImportDataResponse importDataResponse = new ImportDataResponse(true, "Successfully fetched data", importedData);
        when(service.importData(any(ImportDataRequest.class))).thenReturn(importDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Import/importData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ImportDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ImportDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }
}
