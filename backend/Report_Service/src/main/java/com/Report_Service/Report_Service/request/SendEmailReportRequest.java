package com.Report_Service.Report_Service.request;

public class SendEmailReportRequest {
    private String text;
    private String to;
    private String from;
    private String subject;
    private byte[] data;

    public SendEmailReportRequest(String text, String to, String from, String subject, byte[] data) {
        this.text = text;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
