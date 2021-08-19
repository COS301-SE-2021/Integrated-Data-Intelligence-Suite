package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.ImportServiceApplication;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ImportServiceController.class)
public class ImportServiceControllerTest {

    @Autowired
    private ImportServiceController controller;

    @Autowired
    private ImportServiceImpl service;

    private MockMvc mockMvc;


    /*@Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }*/


    @Test
    @DisplayName("When import is requested")
    public void importRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/Import/importData")
                .content(asJsonString(new ImportDataRequest(generateRandomString(), generateRandNum())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("When dated data is requested")
    public void  datedDataRequest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/Import/importDatedData")
                .content(asJsonString(new ImportTwitterRequest(generateRandomString(), generateRandomDate() , LocalDate.now())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    private String generateRandomString(){


        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        int length = 7;

        for(int i = 0; i < length; i++) {

            int index = random.nextInt(alphabet.length());

            char randomChar = alphabet.charAt(index);

            sb.append(randomChar);
        }
        return sb.toString();

    }

    private int generateRandNum(){
        int max = 28;
        int min = 1;
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }

    private LocalDate generateRandomDate(){
        int max = 28;
        int min = 1;
        int range = max - min + 1;
        int day = (int)(Math.random() * range) + min;

        max = 12;
        min = 1;
        range = max - min + 1;
        int month = (int)(Math.random() * range) + min;

        int year = 2021- month;
        return LocalDate.of(year,month,day);

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
