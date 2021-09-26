package com.Gateway_Service.Gateway_Service.dataclass.impor;

public class ImportDataRequest {
    String keyword;
    int limit;

    public ImportDataRequest() {

    }

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
