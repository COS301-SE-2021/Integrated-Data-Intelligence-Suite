package com.Report_Service.Report_Service.response;

public class GenerateReportPDFResponse {
    public byte[] pdf;
    public GenerateReportPDFResponse(byte[] pdf){
        this.pdf = pdf;
    }

    public byte[] getPdf() {
        return pdf;
    }
}
