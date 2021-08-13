package com.Visualize_Service.Visualize_Service.service;

import com.Visualize_Service.Visualize_Service.exception.InvalidRequestException;
import com.Visualize_Service.Visualize_Service.request.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class VisualizeServiceTest {
    @InjectMocks
    private VisualizeServiceImpl service ;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Dummy test")
    public void dummyTest(){
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("When visualizeDataRequest is Null")
    public void visualizeDataNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.visualizeData(null));
    }

    @Test
    @DisplayName("When the PatternList is Null")
    public void visualizeDataPatternListNull(){
        ArrayList<ArrayList> testlist1 = new ArrayList<>();
        ArrayList<ArrayList> testlist2 = new ArrayList<>();
        VisualizeDataRequest test = new VisualizeDataRequest(null,testlist1,testlist2);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.visualizeData(test));
    }

    @Test
    @DisplayName("When the RelationshipList is Null")
    public void visualizeDataRelationshipListNull(){
        ArrayList<ArrayList> testlist1 = new ArrayList<>();
        ArrayList<ArrayList> testlist2 = new ArrayList<>();
        VisualizeDataRequest test = new VisualizeDataRequest(testlist1,null,testlist2);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.visualizeData(test));
    }

    @Test
    @DisplayName("When the PredictionList is Null")
    public void visualizeDataPredictionListNull(){
        ArrayList<ArrayList> testlist1 = new ArrayList<>();
        ArrayList<ArrayList> testlist2 = new ArrayList<>();
        VisualizeDataRequest test = new VisualizeDataRequest(testlist1,testlist2,null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.visualizeData(test));
    }

    @Test
    @DisplayName("When createlineGraphRequest is Null")
    public void createlineGraphNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createlineGraph(null));
    }

    @Test
    @DisplayName("When the DataList is Null")
    public void createlineGraphDataListNull(){
        CreateLineGraphRequest test = new CreateLineGraphRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createlineGraph(test));
    }


    @Test
    @DisplayName("When createNetworkGraphRequest is Null")
    public void createNetworkGraphNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createNetworkGraph(null));
    }

    @Test
    @DisplayName("When the DataList is Null")
    public void createNetworkGraphDataListNull(){
        CreateNetworkGraphRequest test = new CreateNetworkGraphRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createNetworkGraph(test));
    }

    @Test
    @DisplayName("When createTimelineGraphRequest is Null")
    public void createTimelineGraphNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createTimelineGraph(null));
    }

    @Test
    @DisplayName("When the DataList is Null")
    public void createTimelineGraphDataListNull(){
        CreateTimelineGraphRequest test = new CreateTimelineGraphRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createTimelineGraph(test));
    }
    
    @Test
    @DisplayName("When createMapGraphRequest is Null")
    public void createMapGraphNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createMapGraph(null));
    }

    @Test
    @DisplayName("When the DataList is Null")
    public void createMapGraphDataListNull(){
        CreateMapGraphRequest test = new CreateMapGraphRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.createMapGraph(test));
    }


}
