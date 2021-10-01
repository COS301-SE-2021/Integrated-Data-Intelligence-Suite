package com.Report_Service.Report_Service.response;

public class GetReportDataByIdResponse {
    byte[] pdf;

    String name;

    String date;

    public GetReportDataByIdResponse(){

    }

    public GetReportDataByIdResponse(byte[] pdf, String name, String date){
        this.date = date;
        this.name = name;
        this.pdf = pdf;
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
