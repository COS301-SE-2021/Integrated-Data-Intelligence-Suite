package com.Import_Service.Import_Service.request;

import com.Import_Service.Import_Service.rri.AuthorizationType;

import java.util.LinkedHashMap;
import java.util.Map;

public class EditAPISourceRequest {
    private String name;

    private String url;

    private String method;

    private String searchKey;

    private String authorization;

    private AuthorizationType authType;

    private Map<String, String> parameters = new LinkedHashMap<>();

    public EditAPISourceRequest() {

    }

    public EditAPISourceRequest(String name, String url, String method, String searchKey, AuthorizationType authType, String authorization, Map<String, String> parameters) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.searchKey = searchKey;
        this.authType = authType;
        this.authorization = authorization;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public AuthorizationType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthorizationType authType) {
        this.authType = authType;
    }
}
