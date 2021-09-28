package com.Report_Service.Report_Service.repository;

import com.Report_Service.Report_Service.dataclass.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

}
