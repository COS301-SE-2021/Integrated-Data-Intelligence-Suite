package com.User_Service.User_Service.response;

import java.util.Map;

public class GetModelsResponse {
    private Map<String, Boolean> models;
    private String message;
    private boolean success;

    public GetModelsResponse() {

    }

    public GetModelsResponse(boolean success, String message, Map<String, Boolean> models) {
        this.success = success;
        this.message = message;
        this.models = models;
    }

    public Map<String, Boolean> getModels() {
        return models;
    }

    public void setModels(Map<String, Boolean> models) {
        this.models = models;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
