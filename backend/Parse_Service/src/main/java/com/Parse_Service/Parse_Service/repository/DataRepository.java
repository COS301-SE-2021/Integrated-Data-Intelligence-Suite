package com.Parse_Service.Parse_Service.repository;

import com.Parse_Service.Parse_Service.dataclass.ParsedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<ParsedData, Long> {
}
