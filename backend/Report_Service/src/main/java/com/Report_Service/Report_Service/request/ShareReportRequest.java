package com.Report_Service.Report_Service.request;

public class ShareReportRequest {
    private String reportId;

    private String to;

    public ShareReportRequest() {

    }

    public ShareReportRequest(String reportId, String to) {
        this.reportId = reportId;
        this.to = to;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
