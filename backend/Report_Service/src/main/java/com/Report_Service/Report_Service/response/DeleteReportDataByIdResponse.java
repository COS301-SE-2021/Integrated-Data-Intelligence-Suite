package com.Report_Service.Report_Service.response;

public class DeleteReportDataByIdResponse {
    boolean deleted;

    public DeleteReportDataByIdResponse(){

    }

    public DeleteReportDataByIdResponse(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return deleted;
    }
}
