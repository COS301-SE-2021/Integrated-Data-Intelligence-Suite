package com.Analyse_Service.Analyse_Service.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TrainingDataRepositoryTest {

    @Autowired
    private TrainingDataRepository trainingDataRepository;

    @Test
    @DisplayName("Fetch parsed by id")
    public void fetchParsedById()  {

    }
}
