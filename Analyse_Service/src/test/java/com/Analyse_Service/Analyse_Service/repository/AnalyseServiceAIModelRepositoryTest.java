package com.Analyse_Service.Analyse_Service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnalyseServiceAIModelRepositoryTest {
    @Autowired
    private AnalyseServiceParsedDataRepository analyseServiceParsedDataRepository;

}
