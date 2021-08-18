package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnalyseServiceRepositoryTest {

    @Autowired
    private AnalyseServiceRepository analyseServiceRepository;

    @Test
    @DisplayName("Fetch parsed by id")
    public void fetchParsedById()  {

    }
}
