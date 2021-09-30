package com.Report_Service.Report_Service.repository;

import com.Report_Service.Report_Service.dataclass.PdfReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<PdfReport, UUID> {

}
