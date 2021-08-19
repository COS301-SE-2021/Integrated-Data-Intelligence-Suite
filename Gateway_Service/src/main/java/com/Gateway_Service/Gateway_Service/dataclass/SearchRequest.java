package com.Gateway_Service.Gateway_Service.dataclass;

public class SearchRequest {
    private String username;
    private String permission;

    public SearchRequest() {

    }

    public SearchRequest(String username, String permission) {
        this.username = username;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
