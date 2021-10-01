package com.Gateway_Service.Gateway_Service.dataclass.report;

import java.util.UUID;

public class ReportDataResponse {
    UUID id;

    boolean fallback = false;
    String fallbackMessage = "";

    public ReportDataResponse(){

    }

    public ReportDataResponse(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
