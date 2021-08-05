package com.User_Service.User_Service.service;

import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class UserServiceTest {
    @InjectMocks
    UserServiceImpl service = new UserServiceImpl();
    /*
    ============================ManagePermissions tests============================
    */

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Null")
    public void managePermissionsNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(null));
    }

    @Test
    @DisplayName("If_Both_ManagePermissionsRequest_Attributes_Are_Null")
    public void managePermissionsRequestNullAttribs() {
        ManagePermissionsRequest request = new ManagePermissionsRequest(null, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Username_Field_Is_Null")
    public void managePermissionsRequestNullUsernameField() {
        ManagePermissionsRequest request = new ManagePermissionsRequest(null, Permission.VIEWING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Permission_Field_Is_Null")
    public void managePermissionsRequestNullPermissionField() {
        ManagePermissionsRequest request = new ManagePermissionsRequest("exampleUser", null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Exists")
    public void managePermissionsValidRequestUserExists() throws Exception {
        ManagePermissionsRequest request = new ManagePermissionsRequest("testUser", Permission.VIEWING);
        String expectedMessage = "Permission updated";
        ManagePersmissionsResponse response = service.managePermissions(request);
        Assertions.assertEquals(response.getMessage(), expectedMessage);
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Does_Not_Exist")
    public void managePermissionsValidRequestUserNotExists() throws Exception {
        ManagePermissionsRequest request = new ManagePermissionsRequest("nonExistantUser", Permission.VIEWING);
        String expectedMessage = "User does not exist";
        ManagePersmissionsResponse response = service.managePermissions(request);
        Assertions.assertEquals(response.getMessage(), expectedMessage);
    }

    /*
    ============================Register tests============================
    */

    @Test
    @DisplayName("If_RegisterRequest_Is_Null")
    public void registerNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(null));
    }

    @Test
    @DisplayName("If_All_RegisterRequest_Attributes_Are_Null")
    public void registerRequestAllAttribNull() {
        RegisterRequest request = new RegisterRequest(null, null, null, null, null, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Username_Field_Is_Null")
    public void registerRequestUsernameNull() {
        RegisterRequest request = new RegisterRequest(null, "firstname", "lastname", "password", "email", Permission.IMPORTING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_FirstName_Field_Is_Null")
    public void registerRequestFirstNameNull() {
        RegisterRequest request = new RegisterRequest("username", null, "lastname", "password", "email", Permission.IMPORTING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_LastName_Field_Is_Null")
    public void registerRequestLastNameNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", null, "password", "email", Permission.IMPORTING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Password_Field_Is_Null")
    public void registerRequestPasswordNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", null, "email", Permission.IMPORTING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Email_Field_Is_Null")
    public void registerRequestEmailNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", "password", null, Permission.IMPORTING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Permission_Field_Is_Null")
    public void registerRequestPermissionNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", "password", "email", null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }













}
