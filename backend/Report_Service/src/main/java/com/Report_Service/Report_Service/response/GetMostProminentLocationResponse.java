package com.Report_Service.Report_Service.response;

public class GetMostProminentLocationResponse {
    public String location;

    public GetMostProminentLocationResponse(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
