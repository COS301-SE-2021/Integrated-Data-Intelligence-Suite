package com.Gateway_Service.Gateway_Service.dataclass.report;

public class GetReportDataByIdResponse {
    byte[] pdf;

    String name;

    String date;

    boolean fallback = false;
    String fallbackMessage = "";

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


    public void setFallback(boolean fallback){
        this.fallback = fallback;
    }
    public void setFallbackMessage(String fallbackMessage){
        this.fallbackMessage = fallbackMessage;
    }

    public boolean getFallback(){
        return this.fallback;
    }

    public String getFallbackMessage(){
        return this.fallbackMessage;
    }
}
