package com.Report_Service.Report_Service.response;

import java.util.UUID;

public class ReportDataResponse {
    UUID id;

    public ReportDataResponse(){

    }

    public ReportDataResponse(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
