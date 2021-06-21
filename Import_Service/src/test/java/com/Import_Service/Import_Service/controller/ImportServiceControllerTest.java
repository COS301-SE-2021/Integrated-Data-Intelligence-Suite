package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.ImportServiceApplication;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImportServiceApplication.class)
public class ImportServiceControllerTest {

    @InjectMocks
    private ImportServiceController controller;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /*@Test
    @DisplayName("When import is requested")
    public void importRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/Import")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @org.junit.Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest() throws Exception {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        ImportDataRequest importDataRequest = null;
        HttpEntity<ImportDataRequest> instance =new HttpEntity<>(importDataRequest,requestHeaders);

        Gson gson = new Gson();
        String json = gson.toJson(instance);;

        mockMvc.perform(MockMvcRequestBuilders.post("/importData")
                .contentType(MediaType.APPLICATION_JSON).content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidImporterRequestException));
        //.andExpect(result -> Assertions.assertEquals("resource not found", result.getResolvedException().getMessage()));
    }*/
}
