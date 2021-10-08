package com.Gateway_Service.Gateway_Service.dataclass.impor;



import javax.persistence.*;
import java.util.Map;

public class APISource {
    private Long id;

    private String name;

    private String url;

    private String method;

    private String searchKey;

    private AuthorizationType authType;

    private String authorization;

    private Map<String, String> parameters;

    public APISource(String name, String url, String method, String search, AuthorizationType authType, String authorization, Map<String, String> parameters) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.searchKey = search;
        this.authType = authType;
        this.authorization = authorization;
        this.parameters = parameters;
    }

    public APISource() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorizationType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthorizationType authType) {
        this.authType = authType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
