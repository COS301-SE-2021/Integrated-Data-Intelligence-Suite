package com.Report_Service.Report_Service.response;

public class ReportDataResponse {
    byte[] pdf;
    public ReportDataResponse(byte[] pdf){
        this.pdf = pdf;
    }

    public byte[] getPdf() {
        return pdf;
    }
}
