package com.Import_Service.Import_Service.response;

import com.Import_Service.Import_Service.dataclass.APISource;

public class GetAPISourceByIdResponse {
    private boolean success;
    private String message;
    private APISource source;

    public GetAPISourceByIdResponse() {

    }

    public GetAPISourceByIdResponse(boolean success, String message, APISource source) {
        this.success = success;
        this.message = message;
        this.source = source;
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

    public APISource getSource() {
        return source;
    }

    public void setSource(APISource source) {
        this.source = source;
    }
}
