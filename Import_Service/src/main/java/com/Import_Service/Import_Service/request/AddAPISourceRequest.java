package com.Import_Service.Import_Service.request;

import java.util.Map;

public class AddAPISourceRequest {

    private String url;

    private String method;

    private String authorization;

    private Map<String, String> parameters;

    public AddAPISourceRequest() {

    }

    public AddAPISourceRequest(String url, String method, String authorization, Map<String, String> parameters) {
        this.url = url;
        this.method = method;
        this.authorization = authorization;
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
