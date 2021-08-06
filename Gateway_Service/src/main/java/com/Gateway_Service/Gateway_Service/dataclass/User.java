package com.Gateway_Service.Gateway_Service.dataclass;

import com.Gateway_Service.Gateway_Service.rri.Permission;
import java.util.UUID;

public class User {
    UUID id;

    String firstName;

    String lastName;

    String username;

    String email;

    String password;

    Boolean isAdmin;

    Permission permission;

    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password, Permission permission) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.permission = permission;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
