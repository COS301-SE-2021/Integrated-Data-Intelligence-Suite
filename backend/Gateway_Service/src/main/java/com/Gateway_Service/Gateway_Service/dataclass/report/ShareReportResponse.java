package com.Gateway_Service.Gateway_Service.dataclass.report;

public class ShareReportResponse {
    private String message;
    private boolean success;

    boolean fallback = false;
    String fallbackMessage = "";

    public ShareReportResponse() {

    }

    public ShareReportResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
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
