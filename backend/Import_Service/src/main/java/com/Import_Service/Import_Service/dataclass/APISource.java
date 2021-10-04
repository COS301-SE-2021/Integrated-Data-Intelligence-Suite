package com.Import_Service.Import_Service.dataclass;

import com.Import_Service.Import_Service.rri.ApiType;
import com.Import_Service.Import_Service.rri.AuthorizationType;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "apisource")
@Table(name = "apisource")
public class APISource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String url;

    private String method;

    private String searchKey;

    @Enumerated(EnumType.STRING)
    private ApiType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthorizationType authType;

    @Column(name = "header_auth")
    private String authorization;

    @ElementCollection
    @CollectionTable(name = "parameter_mapping",
        joinColumns = {@JoinColumn(name = "apisource_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    private Map<String, String> parameters;

    public APISource(String name, String url, String method, String search, ApiType type, AuthorizationType authType, String authorization, Map<String, String> parameters) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.searchKey = search;
        this.type = type;
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

    public ApiType getType() {
        return type;
    }

    public void setType(ApiType type) {
        this.type = type;
    }

    public void removeAllParameters() {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            parameters.remove(entry.getKey());
        }
    }

    public void removeParameter(String key) {
        parameters.remove(key);
    }
}
