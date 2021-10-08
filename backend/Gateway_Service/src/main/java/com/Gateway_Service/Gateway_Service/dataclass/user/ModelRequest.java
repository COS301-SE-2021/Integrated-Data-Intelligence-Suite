package com.Gateway_Service.Gateway_Service.dataclass.user;

public class ModelRequest {
    private String modelID;

    private String userID;

    public ModelRequest() {

    }

    public ModelRequest(String userID, String modelID) {
        this.userID = userID;
        this.modelID = modelID;
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
