package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalyseRepository extends JpaRepository<ParsedData,Long> {

    List<ParsedData> findTextById(Long Id);
}
