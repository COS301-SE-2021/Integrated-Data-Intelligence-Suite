package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class AnalyseServiceTest {
    @InjectMocks
    private AnalyseServiceImpl service ;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When analyzeRequest is Null")
    public void analyzeDataNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.analyzeData(null));
    }

    @Test
    @DisplayName("When the data list is Null")
    public void analyzeDataNullList(){
        AnalyseDataRequest test = new AnalyseDataRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.analyzeData(test));
    }

    @Test
    @DisplayName("When the data list is Valid")
    public void analyzeDataValidRequest() throws InvalidRequestException {
        ArrayList<ParsedData> TestList = new ArrayList<>();
        ParsedData test1 = new ParsedData();
        test1.setTextMessage("Test Text3 Data");
        test1.setDate("TestDateData");
        test1.setLocation("TestLocationData");
        test1.setLikes(20);

        ParsedData test2 = new ParsedData();
        test2.setTextMessage("Test Text2 Data");
        test2.setDate("TestDateData");
        test2.setLocation("TestLocationData");
        test2.setLikes(20);

        ParsedData test3 = new ParsedData();
        test3.setTextMessage("Test Text3 Data");
        test3.setDate("TestDateData");
        test3.setLocation("TestLocationData");
        test3.setLikes(20);

        TestList.add(test1);
        TestList.add(test2);
        TestList.add(test3);

        AnalyseDataRequest test = new AnalyseDataRequest(TestList);
        AnalyseDataResponse testResults = service.analyzeData(test);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("When findPatternRequest is Null")
    public void findPatternNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findPattern(null));
    }

    @Test
    @DisplayName("When the data list is Null")
    public void findPatternDataNullList(){
        FindPatternRequest test = new FindPatternRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findPattern(test));
    }

    @Test
    @DisplayName("When the data list is Valid")
    public void findPatternValidRequest() throws InvalidRequestException {
        ArrayList<String> TestList = new ArrayList<>();
        String row1 = "Neuatral testi testii 20";
        String row2 = "Neuatral testi testii 20";
        String row3 = "Neuatral testi testii 20";

        TestList.add(row1);
        TestList.add(row2);
        TestList.add(row3);

        FindPatternRequest test = new FindPatternRequest(TestList);
        FindPatternResponse testResults = service.findPattern(test);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("When findRelationshipRequest is Null")
    public void findRelationshipNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findRelationship(null));
    }

    @Test
    @DisplayName("When the data list is Null")
    public void findRelationshipDataNullList(){
        FindRelationshipsRequest test = new FindRelationshipsRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findRelationship(test));
    }

    @Test
    @DisplayName("When the data list is Valid")
    public void findRelationshipValidRequest() throws InvalidRequestException {
        ArrayList<String> TestList = new ArrayList<>();
        String row1 = "Neuatral testi testii 20";
        String row2 = "Neuatral testi testii 20";
        String row3 = "Neuatral testi testii 20";

        TestList.add(row1);
        TestList.add(row2);
        TestList.add(row3);

        FindRelationshipsRequest test = new FindRelationshipsRequest(TestList);
        FindRelationshipsResponse testResults = service.findRelationship(test);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("When getPredictionNullRequest is Null")
    public void getPredictionNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.getPredictions(null));
    }

    @Test
    @DisplayName("When the data list is Null")
    public void getPredictionDataNullList(){
        GetPredictionRequest test = new GetPredictionRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.getPredictions(test));
    }

    @Test
    @DisplayName("When the data list is Valid")
    public void getPredictionValidRequest() throws InvalidRequestException {
        ArrayList<String> TestList = new ArrayList<>();
        String row1 = "Neuatral testi testii 20";
        String row2 = "Neuatral testi testii 20";
        String row3 = "Neuatral testi testii 20";

        TestList.add(row1);
        TestList.add(row2);
        TestList.add(row3);

        GetPredictionRequest test = new GetPredictionRequest(TestList);
        GetPredictionResponse testResults = service.getPredictions(test);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("When findSentimentRequest is Null")
    public void findSentimentRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findSentiment(null));
    }

    @Test
    @DisplayName("When the text is Null")
    public void findSentimentDataNullList(){
        FindSentimentRequest test = new FindSentimentRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findSentiment(test));
    }

    @Test
    @DisplayName("When the text is Valid")
    public void findSentimentValidRequest() throws InvalidRequestException {
        String text = "test text for function";
        FindSentimentRequest test = new FindSentimentRequest(text);
        FindSentimentResponse testResults = service.findSentiment(test);
        Assertions.assertNotNull(testResults);
    }


    @Test
    @DisplayName("When findTrendsNullRequest is Null")
    public void findTrendsNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findTrends(null));
    }

    @Test
    @DisplayName("When the data list is Null")
    public void findTrendsDataNullList(){
        FindTrendsRequest test = new FindTrendsRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findTrends(test));
    }

    @Test
    @DisplayName("When findTrendsNullRequest is Null")
    public void findEntitiesNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findEntities(null));
    }
}
