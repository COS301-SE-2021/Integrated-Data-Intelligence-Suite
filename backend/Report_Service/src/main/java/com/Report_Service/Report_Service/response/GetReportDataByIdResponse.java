package com.Report_Service.Report_Service.response;

import java.util.UUID;

public class GetReportDataByIdResponse {
    byte[] pdf;

    String name;

    String date;

    UUID id;

    public GetReportDataByIdResponse(){

    }

    public GetReportDataByIdResponse(byte[] pdf, String name, String date, UUID id){
        this.date = date;
        this.name = name;
        this.pdf = pdf;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
