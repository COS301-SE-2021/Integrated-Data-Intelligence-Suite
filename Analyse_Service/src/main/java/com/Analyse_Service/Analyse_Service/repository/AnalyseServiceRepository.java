package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalyseServiceRepository extends JpaRepository<ParsedData,Long> {

    @Query(
            value = "SELECT * FROM parsed_data P WHERE P.id = ?1",
            nativeQuery = true)
    List<ParsedData> findTextById(Long Id);
}
