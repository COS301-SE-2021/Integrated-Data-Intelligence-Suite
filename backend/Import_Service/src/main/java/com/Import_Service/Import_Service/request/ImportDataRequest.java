package com.Import_Service.Import_Service.request;

public class ImportDataRequest {
    private String keyword;
    private int limit;

    /**
     * Constructs a new Request with the specified details. the request is
     * to request data from different external sources related to the
     * specified parameters
     *
     * @param keyword search 'keyword' for different data sources
     *                later on retrieved using {@link #getKeyword()}
     * @param limit the maximum number of results per data source
     *              later on retrieved using {@link #getLimit()}
     */
    public ImportDataRequest(String keyword, int limit) {
        this.keyword = keyword;
        this.limit = limit;
    }

    public ImportDataRequest() {
    }

    /**
     * used to retrieve the keyword
     *
     * @return search 'keyword' for different data sources
     */
    public String getKeyword() {
        return keyword;
    }


    /**
     * used to retrieve the limit
     *
     * @return the maximum number of results per data source
     */
    public int getLimit() {
        return limit;
    }
}
