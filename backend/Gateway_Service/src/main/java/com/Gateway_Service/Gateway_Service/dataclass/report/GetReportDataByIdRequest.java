package com.Gateway_Service.Gateway_Service.dataclass.report;

import java.util.UUID;

public class GetReportDataByIdRequest {
    UUID reportId;

    public GetReportDataByIdRequest(){

    }

    public GetReportDataByIdRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
