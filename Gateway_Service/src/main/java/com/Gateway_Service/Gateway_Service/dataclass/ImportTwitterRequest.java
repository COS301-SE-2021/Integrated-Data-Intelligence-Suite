package com.Gateway_Service.Gateway_Service.dataclass;

public class ImportTwitterRequest {
    String keyword;
    int limit;
    String from;
    String to;



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



    public ImportTwitterRequest(String keyword, String from, String to) {
        this.keyword = keyword;
        this.limit = limit;
        this.from = from;
        this.to = to;
    }

    public ImportTwitterRequest(String keyword, int limit, String from, String to) {
        this.keyword = keyword;
        this.limit = limit;
        this.from = from;
        this.to = to;
    }

    public int getLimit() {
        return limit;
    }

    public String getKeyword() {return keyword; }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
