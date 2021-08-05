package com.User_Service.User_Service.service;

import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class UserServiceTest {
    @InjectMocks
    UserServiceImpl service = new UserServiceImpl();

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Null")
    public void managePermissionsNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(null));
    }

    @Test
    @DisplayName("If_Both_ManagePermissionsRequest_Attributes_are null")
    public void mangePermissionsRequestNullAttribs() {
        ManagePermissionsRequest request = new ManagePermissionsRequest(null, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
    }










}
