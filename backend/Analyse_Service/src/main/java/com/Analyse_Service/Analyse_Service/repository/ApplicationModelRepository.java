package com.Analyse_Service.Analyse_Service.repository;

import com.Analyse_Service.Analyse_Service.dataclass.ApplicationModel;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationModelRepository extends JpaRepository<ApplicationModel,String> {
}
