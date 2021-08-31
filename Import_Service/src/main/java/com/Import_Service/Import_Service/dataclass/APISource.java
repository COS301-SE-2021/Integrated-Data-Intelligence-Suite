package com.Import_Service.Import_Service.dataclass;

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

    @Column(name = "header_auth")
    private String authorization;

    @ElementCollection
    @CollectionTable(name = "parameter_mapping",
        joinColumns = {@JoinColumn(name = "apisource_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "param_key")
    @Column(name = "param_value")
    private Map<String, String> parameters;

    public APISource(String name, String url, String method, String authorization, Map<String, String> parameters) {
        this.name = name;
        this.url = url;
        this.method = method;
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
}
