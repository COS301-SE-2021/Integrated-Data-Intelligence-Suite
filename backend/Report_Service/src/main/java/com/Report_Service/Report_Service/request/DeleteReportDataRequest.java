package com.Report_Service.Report_Service.request;

import java.util.UUID;

public class DeleteReportDataRequest {
    UUID reportId;

    public DeleteReportDataRequest(UUID reportId){
        this.reportId= reportId;
    }

    public UUID getReportId(){
        return reportId;
    }
}
