package com.User_Service.User_Service.response;

public class GetCurrentUserResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Boolean isAdmin;
    private Boolean success;
    private String message;

    public GetCurrentUserResponse() {

    }

    public GetCurrentUserResponse(Boolean success, String message, String firstName, String lastName, String username, String email, Boolean isAdmin) {
        this.success = success;
        this.message = message;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public GetCurrentUserResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.firstName = null;
        this.lastName = null;
        this.username = null;
        this.email = null;
        this.isAdmin = null;
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
