package com.Import_Service.Import_Service.request;

public class DeleteSourceRequest {
    private Long id;

    public DeleteSourceRequest() {
    }

    public DeleteSourceRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
