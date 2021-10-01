package com.Report_Service.Report_Service.response;

public class DeleteReportDataResponse {
    boolean deleted;

    public DeleteReportDataResponse(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return deleted;
    }
}
