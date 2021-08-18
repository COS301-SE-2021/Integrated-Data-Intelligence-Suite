package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.dataclass.AIModel;
import com.Analyse_Service.Analyse_Service.dataclass.AIType;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.exception.AnalyzerException;
import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceAIModelRepository;
import com.Analyse_Service.Analyse_Service.repository.AnalyseServiceParsedDataRepository;
import com.Analyse_Service.Analyse_Service.request.*;
import com.Analyse_Service.Analyse_Service.response.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnalyseServiceTest {

    @Mock
    private AnalyseServiceAIModelRepository analyseServiceAIModelRepository;

    @Mock
    private AnalyseServiceParsedDataRepository analyseServiceParsedDataRepository;


    @InjectMocks
    private AnalyseServiceImpl service ;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    /************************************************Boundry Test******************************************************/

    @Test
    @DisplayName("When analyzeDataRequest is Null")
    public void analyzeDataNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.analyzeData(null));
    }

    @Test
    @DisplayName("When the analyzeData data list is Null")
    public void analyzeDataNullList(){
        AnalyseDataRequest test = new AnalyseDataRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.analyzeData(test));
    }


    @Test
    @DisplayName("When findPatternRequest is Null")
    public void findPatternNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findPattern(null));
    }

    @Test
    @DisplayName("When the findPattern data list is Null")
    public void findPatternDataNullList(){
        FindPatternRequest test = new FindPatternRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findPattern(test));
    }


    @Test
    @DisplayName("When findRelationshipRequest is Null")
    public void findRelationshipNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findRelationship(null));
    }

    @Test
    @DisplayName("When the findRelationship data list is Null")
    public void findRelationshipDataNullList(){
        FindRelationshipsRequest test = new FindRelationshipsRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findRelationship(test));
    }


    @Test
    @DisplayName("When getPredictionNullRequest is Null")
    public void getPredictionNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.getPredictions(null));
    }

    @Test
    @DisplayName("When the getPrediction data list is Null")
    public void getPredictionDataNullList(){
        GetPredictionRequest test = new GetPredictionRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.getPredictions(test));
    }


    @Test
    @DisplayName("When findTrendsNullRequest is Null")
    public void findTrendsNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findTrends(null));
    }

    @Test
    @DisplayName("When the findTrends data list is Null")
    public void findTrendsDataNullList(){
        FindTrendsRequest test = new FindTrendsRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findTrends(test));
    }

    @Test
    @DisplayName("When NlpPropertiesNullRequest is Null")
    public void findNlpPropertiesNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findNlpProperties(null));
    }

    @Test
    @DisplayName("When the NlpProperties text is Null")
    public void findNlpPropertiesTextNull(){
        FindNlpPropertiesRequest test = new FindNlpPropertiesRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.findNlpProperties(test));
    }


    /*********************************************Functionality Test****************************************************/

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
    @DisplayName("When the data list is Valid")
    public void findPatternValidRequest() throws InvalidRequestException {
        ArrayList<ArrayList> TestList = new ArrayList<>();

        ArrayList<String> row = new ArrayList<>();
        row.add("Neuatral testi testii 20");
        row.add("Neuatral testi testii 20");
        row.add("Neuatral testi testii 20");

        TestList.add(row);

        FindPatternRequest test = new FindPatternRequest(TestList);
        FindPatternResponse testResults = service.findPattern(test);
        Assertions.assertNotNull(testResults);
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

        FindRelationshipsRequest test = new FindRelationshipsRequest(null);
        FindRelationshipsResponse testResults = service.findRelationship(test);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("When the data list is Valid")
    public void getPredictionValidRequest() throws InvalidRequestException {
        ArrayList<ArrayList> TestList = new ArrayList<>();

        ArrayList<String> row = new ArrayList<>();
        row.add("Neuatral testi testii 20");
        row.add("Neuatral testi testii 20");
        row.add("Neuatral testi testii 20");

        TestList.add(row);

        GetPredictionRequest test = new GetPredictionRequest(TestList);
        GetPredictionResponse testResults = service.getPredictions(test);
        Assertions.assertNotNull(testResults);
    }


    @Test
    @DisplayName("When the text is Valid")
    public void findEntitiesValidRequest() throws InvalidRequestException {
        String text = "test text for function";
        FindNlpPropertiesRequest test = new FindNlpPropertiesRequest(text);
        FindNlpPropertiesResponse testResults = service.findNlpProperties(test);
        Assertions.assertNotNull(testResults);
    }

    /******************************************Repository Functional Test**********************************************/


    @Test
    @DisplayName("fetch parsedData from database")
    public void fetchParsedDataFromDatabase() throws AnalyzerException {
        FetchParsedDataRequest request = new FetchParsedDataRequest("ParsedData");
        FetchParsedDataResponse testResults = service.fetchParsedData(request);
        Assertions.assertNotNull(testResults);
    }

    @Test
    @DisplayName("fetch saveAIModel from database")
    public void fetchSaveAIModelFromDatabase() throws AnalyzerException {
        //set test data
        AIModel saveModel = new AIModel();
        saveModel.setAccuracy(85.0F);
        saveModel.setType(AIType.Prediction);

        //run repository
        SaveAIModelRequest request = new SaveAIModelRequest(saveModel);
        SaveAIModelResponse testResults = service.saveAIModel(request);

        ArgumentCaptor<AIModel> aiModelArgumentCaptor = ArgumentCaptor.forClass(AIModel.class);

        verify(analyseServiceAIModelRepository).save(aiModelArgumentCaptor.capture());

        AIModel testModel = aiModelArgumentCaptor.getValue();

        //test
        Assertions.assertEquals(saveModel,testModel);
        Assertions.assertTrue(testResults.getModelSave());
    }


}
