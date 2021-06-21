package com.Parse_Service.Parse_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;

@Repository
public interface ParsedDataRepository extends JpaRepository<ParsedData, Long> {
}
