package com.Gateway_Service.Gateway_Service.dataclass;

import com.Gateway_Service.Gateway_Service.rri.Permission;

public class ManagePermissionsRequest {
    private String username;
    private Permission newPermission;

    public ManagePermissionsRequest(String username, Permission newPermission) {
        this.username = username;
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
}
