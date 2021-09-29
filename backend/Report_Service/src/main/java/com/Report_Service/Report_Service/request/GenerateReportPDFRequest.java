package com.Report_Service.Report_Service.request;

import com.Report_Service.Report_Service.dataclass.Report;

public class GenerateReportPDFRequest {
    public Report report;

    public GenerateReportPDFRequest(Report report){
        this.report=report;
    }

    public Report getReport(){
        return report;
    }
}
