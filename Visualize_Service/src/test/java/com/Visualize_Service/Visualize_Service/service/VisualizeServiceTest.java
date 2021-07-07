package com.Visualize_Service.Visualize_Service.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

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
}
