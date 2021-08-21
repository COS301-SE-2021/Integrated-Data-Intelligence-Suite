package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.dataclass.AIModel;
import com.Analyse_Service.Analyse_Service.dataclass.AIType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalyseServiceAIModelRepository extends JpaRepository<AIModel,Long> {

    //"SELECT MAX(a.accuracy) FROM AIModel a WHERE a.type = ?1"+
    @Query(value = "SELECT a FROM AIModel a " +
            "WHERE a.accuracy = (SELECT MAX(accuracy) FROM AIModel WHERE type = ?1)")
    AIModel findHighestAccuracyByType(AIType type);
}
