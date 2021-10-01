package com.Report_Service.Report_Service.request;

import java.util.UUID;

public class DeleteReportDataByIdRequest {
    UUID reportId;

    public DeleteReportDataByIdRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
