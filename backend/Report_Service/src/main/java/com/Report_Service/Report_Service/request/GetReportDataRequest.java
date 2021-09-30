package com.Report_Service.Report_Service.request;

import java.util.UUID;

public class GetReportDataRequest {

    UUID reportId;

    public GetReportDataRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
