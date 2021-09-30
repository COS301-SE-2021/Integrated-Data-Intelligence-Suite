package com.Report_Service.Report_Service.repository;

import com.Report_Service.Report_Service.dataclass.PdfReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<PdfReport, UUID> {

    @Query("SELECT s FROM reports s WHERE s.id = ?1")
    Optional<PdfReport> findUserById(UUID userID);
}
