package com.Import_Service.Import_Service.request;

public class ImportTwitterRequest {
    String keyword;
    String token;
    int limit;


    public ImportTwitterRequest(String keyword) {

        this.keyword = keyword;
        this.limit = 10;
        this.token = "";
    }

    public ImportTwitterRequest(String keyword, int limit) {
        this.keyword = keyword;
        this.limit = limit;
        this.token = "";
    }

    public ImportTwitterRequest(String keyword, String token, int limit) {
        this.keyword = keyword;
        this.token = token;
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public String getKeyword() {return keyword; }

    public String getToken() { return token; }
}
