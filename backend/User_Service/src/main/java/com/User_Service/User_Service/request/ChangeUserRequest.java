package com.User_Service.User_Service.request;

import com.User_Service.User_Service.rri.Permission;

public class ChangeUserRequest {
    private String username;
    private boolean admin;
    private Permission newPermission;

    public ChangeUserRequest() {

    }

    public ChangeUserRequest(String username, boolean admin, Permission newPermission) {
        this.username = username;
        this.admin = admin;
        this.newPermission = newPermission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Permission getNewPermission() {
        return newPermission;
    }

    public void setNewPermission(Permission newPermission) {
        this.newPermission = newPermission;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
