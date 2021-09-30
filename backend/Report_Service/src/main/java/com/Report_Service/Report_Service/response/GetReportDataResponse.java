package com.Report_Service.Report_Service.response;

public class GetReportDataResponse {
    byte[] pdf;

    public GetReportDataResponse(byte[] pdf){
        this.pdf = pdf;
    }

    public byte[] getPdf() {
        return pdf;
    }
}
