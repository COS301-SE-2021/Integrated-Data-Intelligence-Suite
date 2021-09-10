package com.Import_Service.Import_Service.request;

public class GetAPISourceByIdRequest {
    private Long id;

    public GetAPISourceByIdRequest() {

    }

    public GetAPISourceByIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
