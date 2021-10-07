package com.Gateway_Service.Gateway_Service.dataclass.report;

import java.util.UUID;

public class GetReportDataByIdResponse {
    byte[] pdf;

    String name;

    String date;

    UUID id;

    boolean fallback = false;
    String fallbackMessage = "";

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
