package com.Gateway_Service.Gateway_Service.dataclass.user;

import java.util.List;

public class GetUserReportsResponse {
    private boolean success;
    private String message;
    private List<String> reports;

    public GetUserReportsResponse() {

    }

    public GetUserReportsResponse(boolean success, String message, List<String> reports) {
        this.success = success;
        this.message = message;
        this.reports = reports;
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

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }
}
