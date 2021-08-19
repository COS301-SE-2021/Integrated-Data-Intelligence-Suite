package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.exception.InvalidNewsRequestException;
import com.Import_Service.Import_Service.exception.InvalidTwitterRequestException;
import com.Import_Service.Import_Service.request.ImportNewsDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
public class ImportServiceTest {

    @Autowired
    private ImportServiceImpl service;

    @BeforeEach
    public void setup(){

    }

    @Test
    @DisplayName("If_Import_Twitter_Data_Json_Request_Is_Null")
    public void importTwitterDataJsonRequestNull() {
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(null));
    }
    @Test
    @DisplayName("If_Import_Dated_Data_Request_Key_Is_Null")
    public void nullKeyImportTwitterDatat(){
        ImportTwitterRequest request = new ImportTwitterRequest(null, 10);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("If_Import_Twitter_Data_Json_Request_Limit_Less_Than_One")
    public void testIfLimitLessThanOne() {
        ImportTwitterRequest request = new ImportTwitterRequest("keyword", 0);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("If_Import_Twitter_Data_Json_Request_Limit_Greater_Than_Hundred")
    public void testIfLimitGreaterThanHundred() {
        ImportTwitterRequest request = new ImportTwitterRequest("keyword", 1000);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("Keyword_Length_Test")
    public void keywordLengthTest() {
        ImportTwitterRequest lesserRequest = new ImportTwitterRequest("a", 4);
        ImportTwitterRequest greaterRequest = new ImportTwitterRequest("z5woOd9OR7S2dAFf53FJptx2Fqms0eTlqOceFtmEnv2gpNK2G86x920zSC8kfHwiz1R3AGNE1T0EATHUZGmcb36ryF8uN7b46gzrnyMp3GesPbv2lLNl4XEj9P7OgKFrHE5RH5EGGv5bnueAxHg12jEMlYRpzJ1RE0Bw3B5uit9RO4V5aGZt2wemnnbN7izolH8s9oBRASnWuFYxPSxbLMtOBjRE4OvAhqqqSMWR4dPv0jgOYxDRHhvYo9eVknuAipaA", 4);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(lesserRequest));
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.getTwitterDataJson(greaterRequest));
    }

    @Test
    @DisplayName("If_Import_News_Data_Request_Is_Null")
    public void nullNewsDataRequest() {
        Assertions.assertThrows(InvalidNewsRequestException.class, () -> service.importNewsData(null));
    }


    @Test
    @DisplayName("If_Import_News_Data_Request_Key_Is_Null")
    public void nullKeyNewsDataReqeust() {
        ImportNewsDataRequest request = new ImportNewsDataRequest(null);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importNewsData(request));
    }

    @Test
    @DisplayName("If_Import_NewsData_Request_Keyword_length_Test")
    public void keywordLengthTestNews(){
        ImportNewsDataRequest lesserRequest = new ImportNewsDataRequest("x");
        ImportNewsDataRequest greaterRequest = new ImportNewsDataRequest("z5woOd9OR7S2dAFf53FJptx2Fqms0eTlqOceFtmEnv2gpNK2G86x920zSC8kfHwiz1R3AGNE1T0EATHUZGmcb36ryF8uN7b46gzrnyMp3GesPbv2lLNl4XEj9P7OgKFrHE5RH5EGGv5bnueAxHg12jEMlYRpzJ1RE0Bw3B5uit9RO4V5aGZt2wemnnbN7izolH8s9oBRASnWuFYxPSxbLMtOBjRE4OvAhqqqSMWR4dPv0jgOYxDRHhvYo9eVknuAipaA");
        Assertions.assertThrows(InvalidNewsRequestException.class, () -> service.importNewsData(lesserRequest));
        Assertions.assertThrows(InvalidNewsRequestException.class, () -> service.importNewsData(greaterRequest));
    }

    @Test
    @DisplayName("If_Import_Dated_Data_Request_Is_Null")
    public void nullDatedRequest(){
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(null));
    }

    @Test
    @DisplayName("If_Import_Dated_Data_Request_Key_Is_Null")
    public void nullKeyDatedDataRequest(){
        ImportTwitterRequest request = new ImportTwitterRequest(null, 10);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(request));
    }

    @Test
    @DisplayName("Keyword_Length_Test_Import_Dated_Data_Request")
    public void KeywordLengthTestDatedData(){
        ImportTwitterRequest lesserRequest = new ImportTwitterRequest("a", LocalDate.parse("2020-07-06"), LocalDate.parse("2021-02-01"));
        ImportTwitterRequest greaterRequest = new ImportTwitterRequest("z5woOd9OR7S2dAFf53FJptx2Fqms0eTlqOceFtmEnv2gpNK2G86x920zSC8kfHwiz1R3AGNE1T0EATHUZGmcb36ryF8uN7b46gzrnyMp3GesPbv2lLNl4XEj9P7OgKFrHE5RH5EGGv5bnueAxHg12jEMlYRpzJ1RE0Bw3B5uit9RO4V5aGZt2wemnnbN7izolH8s9oBRASnWuFYxPSxbLMtOBjRE4OvAhqqqSMWR4dPv0jgOYxDRHhvYo9eVknuAipaA", 4);
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(lesserRequest));
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(greaterRequest));
    }

    @Test
    @DisplayName("Invalid_FromDate_Import_Dated_Data_Request")
    public void InvalidFromDateTestDatedData(){
        ImportTwitterRequest request = new ImportTwitterRequest("valid key", LocalDate.parse("2001-07-06"), LocalDate.parse("2021-02-01"));
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(request));
    }

    @Test
    @DisplayName("Invalid_toDate_Import_Dated_Data_Request")
    public void InvalidToDateTestDatedData(){
        ImportTwitterRequest request = new ImportTwitterRequest("valid key", LocalDate.parse("2010-07-06"), LocalDate.parse("2022-02-01"));
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(request));
    }

    @Test
    @DisplayName("Invalid_Dates_Import_Dated_Data_Request")
    public void InvalidDatesTestDatedData(){
        ImportTwitterRequest request = new ImportTwitterRequest("valid key", LocalDate.parse("2023-07-06"), LocalDate.parse("2022-02-01"));
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> service.importDatedData(request));
    }






}
