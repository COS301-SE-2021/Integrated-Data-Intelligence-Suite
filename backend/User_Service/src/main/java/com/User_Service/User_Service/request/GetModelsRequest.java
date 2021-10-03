package com.User_Service.User_Service.request;

public class GetModelsRequest {
    private String userID;

    public GetModelsRequest() {

    }

    public GetModelsRequest(String userID) {
        this.userID = userID;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
