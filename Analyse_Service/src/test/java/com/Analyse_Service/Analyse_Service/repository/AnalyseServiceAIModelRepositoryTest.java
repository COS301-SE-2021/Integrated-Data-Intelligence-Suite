package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.dataclass.AIModel;
import com.Analyse_Service.Analyse_Service.dataclass.AIType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class AnalyseServiceAIModelRepositoryTest {

    @Autowired
    private AnalyseServiceAIModelRepository analyseServiceAIModelRepository;

    @Test
    @DisplayName("Fetch AIModel by type")
    public void fetchAIModelByIdType()  {

        //AIType aiType = AIType.Prediction;

        //create & set model
        AIModel aiModel1 = new AIModel(); //create model
        //aiModel1.setId((long) 0);
        aiModel1.setAccuracy(37.0F);
        aiModel1.setType(AIType.Prediction);
        //save model in database
        analyseServiceAIModelRepository.save(aiModel1);

        AIModel aiModel2 = new AIModel();
        //aiModel2.setId((long) 1);
        aiModel2.setAccuracy(50.0F);
        aiModel2.setType(AIType.Prediction);
        analyseServiceAIModelRepository.save(aiModel2);

        AIModel aiModel3 = new AIModel();
        //aiModel3.setId((long) 1);
        aiModel3.setAccuracy(93.1F);
        aiModel3.setType(AIType.Prediction);
        analyseServiceAIModelRepository.save(aiModel3);

        AIModel aiModel4 = new AIModel();
        //aiModel4.setId((long) 1);
        aiModel4.setAccuracy(92.0F);
        aiModel4.setType(AIType.Prediction);
        analyseServiceAIModelRepository.save(aiModel4);

        AIModel aiModel5 = new AIModel();
        //aiModel5.setId((long) 1);
        aiModel5.setAccuracy(84.5F);
        aiModel5.setType(AIType.Prediction);
        analyseServiceAIModelRepository.save(aiModel5);

        //Test
        AIModel aiModelTest = analyseServiceAIModelRepository.findHighestAccuracyByType(AIType.Prediction);
        Assertions.assertEquals(aiModel3,aiModelTest);
    }

}
