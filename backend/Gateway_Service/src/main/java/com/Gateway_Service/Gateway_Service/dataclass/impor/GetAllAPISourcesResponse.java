package com.Gateway_Service.Gateway_Service.dataclass.impor;

import java.util.List;

public class GetAllAPISourcesResponse {
    private List<APISource> sources;
    private boolean success;
    private String message;

    public GetAllAPISourcesResponse() {

    }

    public GetAllAPISourcesResponse(boolean success, String message, List<APISource> sources) {
        this.sources = sources;
        this.success = success;
        this.message = message;
    }


    public List<APISource> getSources() {
        return sources;
    }

    public void setSources(List<APISource> sources) {
        this.sources = sources;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
