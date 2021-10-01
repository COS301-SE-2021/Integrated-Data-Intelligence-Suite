package com.Analyse_Service.Analyse_Service.service;

import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import com.Analyse_Service.Analyse_Service.repository.TrainingDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TrainServiceTest {

    @Mock
    private TrainingDataRepository trainingDataRepository;


    @InjectMocks
    private TrainServiceImpl service ;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    /************************************************Boundry Test******************************************************/

    @Test
    @DisplayName("When trainUserModelRequest is Null")
    public void trainUserModelNullRequest(){
        Assertions.assertThrows(InvalidRequestException.class, () -> service.trainUserModel(null));
    }

}
