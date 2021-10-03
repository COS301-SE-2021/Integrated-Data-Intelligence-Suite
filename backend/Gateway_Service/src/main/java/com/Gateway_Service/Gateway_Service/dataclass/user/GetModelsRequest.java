package com.Gateway_Service.Gateway_Service.dataclass.user;

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
