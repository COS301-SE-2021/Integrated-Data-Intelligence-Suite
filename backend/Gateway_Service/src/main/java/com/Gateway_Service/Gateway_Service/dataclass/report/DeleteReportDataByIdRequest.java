package com.Gateway_Service.Gateway_Service.dataclass.report;

import java.util.UUID;

public class DeleteReportDataByIdRequest {
    UUID reportId;

    public DeleteReportDataByIdRequest(){

    }

    public DeleteReportDataByIdRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
