package com.Gateway_Service.Gateway_Service.dataclass.report;

public class DeleteReportDataByIdResponse {
    boolean deleted;

    boolean fallback = false;
    String fallbackMessage = "";

    public DeleteReportDataByIdResponse(){

    }

    public DeleteReportDataByIdResponse(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return deleted;
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
