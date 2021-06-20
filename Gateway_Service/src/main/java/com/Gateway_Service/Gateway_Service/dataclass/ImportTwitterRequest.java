package com.Gateway_Service.Gateway_Service.dataclass;

public class ImportTwitterRequest {
    String keyword;
    int limit;

    public ImportTwitterRequest() {

    }

    public ImportTwitterRequest(String keyword) {

        this.keyword = keyword;
        this.limit = 10;
    }

    public ImportTwitterRequest(String keyword, int limit) {
        this.keyword = keyword;
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public String getKeyword() {

        return keyword;
    }

}
