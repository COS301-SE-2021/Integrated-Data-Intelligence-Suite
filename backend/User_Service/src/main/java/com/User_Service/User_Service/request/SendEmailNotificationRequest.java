package com.User_Service.User_Service.request;

public class SendEmailNotificationRequest {
    private String text;
    private String to;
    private String from;
    private String subject;

    public SendEmailNotificationRequest(String text, String to, String from, String subject) {
        this.text = text;
        this.to = to;
        this.from = from;
        this.subject = subject;
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
}
