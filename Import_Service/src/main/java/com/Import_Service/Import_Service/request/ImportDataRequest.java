package com.Import_Service.Import_Service.request;

public class ImportDataRequest {
    String keyword;
    int limit;

    public ImportDataRequest(String keyword, int limit) {
        this.keyword = keyword;
        this.limit = limit;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getLimit() {
        return limit;
    }
}
