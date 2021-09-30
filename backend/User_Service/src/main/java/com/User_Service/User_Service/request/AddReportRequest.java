package com.User_Service.User_Service.request;

public class AddReportRequest {
    private String reportID;
    private String userID;

    public AddReportRequest() {

    }

    public AddReportRequest(String reportID, String userID) {
        this.reportID = reportID;
        this.userID = userID;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
