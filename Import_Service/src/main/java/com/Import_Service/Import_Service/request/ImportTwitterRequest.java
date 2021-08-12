package com.Import_Service.Import_Service.request;

import java.time.LocalDate;

public class ImportTwitterRequest {
    String keyword;
    int limit;
    LocalDate from;
    LocalDate to;



    public ImportTwitterRequest(String keyword) {

        this.keyword = keyword;
        this.limit = 100;
        from = null;
        to = null;
    }

    public ImportTwitterRequest(String keyword, int limit) {
        this.keyword = keyword;
        this.limit = limit;
        from = null;
        to = null;
    }



    public ImportTwitterRequest(String keyword, LocalDate from, LocalDate to) {
        this.keyword = keyword;
        this.limit = limit;
        this.from = from;
        this.to = to;
    }

    public ImportTwitterRequest(String keyword, int limit, LocalDate from, LocalDate to) {
        this.keyword = keyword;
        this.limit = limit;
        this.from = from;
        this.to = to;
    }

    public int getLimit() {
        return limit;
    }

    public String getKeyword() {return keyword; }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
