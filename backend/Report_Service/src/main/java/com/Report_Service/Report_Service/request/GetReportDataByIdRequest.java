package com.Report_Service.Report_Service.request;

import java.util.UUID;

public class GetReportDataByIdRequest {

    UUID reportId;

    public GetReportDataByIdRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
