package service;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import com.User_Service.User_Service.service.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserServiceImpl service;

    User testUser = new User();

    @BeforeEach
    public void init() {
        service = new UserServiceImpl();
        service.setRepository(userRepository);

        testUser.setId(UUID.fromString("0b4b8936-bd7e-4373-b097-bb227b9f4072"));
        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("1000:3dd6e56301215db3aaf500526a14f9b8:dc79a5a53f48f100883ef1327c89248b1eb0eb231408c746a4080995db17eafd71042a5b9c5429e2e26ea58d1e6e357e027e289f5a836fefa3b8accf4a056485");
        testUser.setVerified(true);
        testUser.setVerificationCode("eroiwuerowiuerowiurow");
        testUser.setDateCreated(new Date());
        testUser.setPermission(Permission.IMPORTING);
    }

    /*
    ============================ManagePermissions tests============================
    */

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Null")
    public void managePermissionsNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.changeUser(null));
    }

    @Test
    @DisplayName("If_Both_ManagePermissionsRequest_Attributes_Are_Null")
    public void managePermissionsRequestNullAttribs() {
        ChangeUserRequest request = new ChangeUserRequest(null, false, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.changeUser(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Username_Field_Is_Null")
    public void managePermissionsRequestNullUsernameField() {
        ChangeUserRequest request = new ChangeUserRequest(null, false,  Permission.VIEWING);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.changeUser(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Permission_Field_Is_Null")
    public void managePermissionsRequestNullPermissionField() {
        ChangeUserRequest request = new ChangeUserRequest("exampleUser", false, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.changeUser(request));
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Exists")
    public void managePermissionsValidRequestUserExists() throws Exception {
        when(userRepository.findUserByUsername("UserNameTest")).thenReturn(Optional.ofNullable(testUser));
        when(userRepository.updatePermission(testUser.getId(), Permission.VIEWING)).thenReturn(1);

        //test
        ChangeUserRequest request = new ChangeUserRequest("UserNameTest", false, Permission.VIEWING);
        ChangeUserResponse response = service.changeUser(request);

        Assertions.assertEquals("Permission updated", response.getMessage());
    }

    @Test
    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Does_Not_Exist")
    public void managePermissionsValidRequestUserNotExists() throws Exception {

        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test
        ChangeUserRequest request = new ChangeUserRequest("NotUserNameTest", false, Permission.VIEWING);
        ChangeUserResponse response = service.changeUser(request);

        Optional<User> foundUser = verify(userRepository).findUserByUsername("NotUserNameTest");
        Assertions.assertNull(foundUser);

        Assertions.assertEquals("User does not exist", response.getMessage());

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
        RegisterRequest request = new RegisterRequest(null, null, null, null, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Username_Field_Is_Null")
    public void registerRequestUsernameNull() {
        RegisterRequest request = new RegisterRequest(null, "firstname", "lastname", "password", "email");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_FirstName_Field_Is_Null")
    public void registerRequestFirstNameNull() {
        RegisterRequest request = new RegisterRequest("username", null, "lastname", "password", "email");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_LastName_Field_Is_Null")
    public void registerRequestLastNameNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", null, "password", "email");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Password_Field_Is_Null")
    public void registerRequestPasswordNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", null, "email");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_RegisterRequest_Email_Field_Is_Null")
    public void registerRequestEmailNull() {
        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", "password", null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
    }

    @Test
    @DisplayName("If_New_User_Username_Already_Taken")
    public void registerUsernameTaken() throws Exception {
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(testUser));

        //test
        RegisterRequest request = new RegisterRequest("UserNameTest", "FirstNameTestDifferent", "LastNameTestDifferent", "emailDifferent@test.com", "passwordTestDifferent@test.com");
        RegisterResponse response = service.register(request);

        Assertions.assertEquals("Username has been taken", response.getMessage());
    }

    @Test
    @DisplayName("If_New_User_Email_Already_Taken")
    public void registerEmailTaken() throws Exception {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(testUser));

        //test
        RegisterRequest request = new RegisterRequest("asudfhaisudfh", "FirstNameTestDifferent", "LastNameTestDifferent", "adasdadas", "email@test.com");
        RegisterResponse response = service.register(request);

        Assertions.assertEquals("This email has already been registered", response.getMessage());
    }

    @Test
    @DisplayName("User_Successfully_Registered")
    public void registerSuccessful() throws Exception {
        User newTestUser = new User();

        newTestUser.setId(UUID.fromString("0b4b8936-bd7e-4373-b097-bb227b9f4072"));
        newTestUser.setFirstName("FirstNameTest");
        newTestUser.setLastName("LastNameTest");
        newTestUser.setUsername("newUsername");
        newTestUser.setEmail("newemail@test.com");
        newTestUser.setPassword("1000:3dd6e56301215db3aaf500526a14f9b8:dc79a5a53f48f100883ef1327c89248b1eb0eb231408c746a4080995db17eafd71042a5b9c5429e2e26ea58d1e6e357e027e289f5a836fefa3b8accf4a056485");
        newTestUser.setVerified(true);
        newTestUser.setVerificationCode("eroiwuerowiuerowiurow");
        newTestUser.setDateCreated(new Date(2021, Calendar.JULY, 2));
        newTestUser.setPermission(Permission.IMPORTING);

        when(userRepository.save(any(User.class))).thenReturn(newTestUser);

        //test
        User savedUser = userRepository.save(newTestUser);
        Assertions.assertEquals(newTestUser.getUsername(), savedUser.getUsername());
        Assertions.assertNotNull(savedUser);
    }


    //============================Login tests============================


    @Test
    @DisplayName("If_LoginRequest_Is_Null")
    public void loginRequestNull() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(null));
    }

    @Test
    @DisplayName("If_LoginRequest_All_Attrib_Are_Null")
    public void loginRequestAttribAllNull() {
        LoginRequest request = new LoginRequest(null, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
    }

    @Test
    @DisplayName("If_LoginRequest_Email_Null")
    public void loginRequestEmailNull() {
        LoginRequest request = new LoginRequest(null, "password");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
    }

    @Test
    @DisplayName("If_LoginRequest_Password_Null")
    public void loginRequestPasswordNull() {
        LoginRequest request = new LoginRequest("test@email.com", null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
    }

    @Test
    @DisplayName("Login_If_Email_Does_Not_Exist")
    public void loginEmailNotExist() throws Exception {
        //test

        LoginRequest request = new LoginRequest("missingEmail@notexist.com", "password");
        LoginResponse response = service.login(request);

        Optional<User> foundUser = verify(userRepository).findUserByEmail("missingEmail@notexist.com");
        Assertions.assertNull(foundUser);

        Assertions.assertEquals("The email does not exist", response.getMessage());

    }

    @Test
    @DisplayName("Login_Existing_Email_Wrong_Password")
    public void loginWrongPassword() throws Exception {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        //test

        LoginRequest request = new LoginRequest("email@test.com", "wrongPasswordTest");
        LoginResponse response = service.login(request);

        Optional<User> foundUser = verify(userRepository).findUserByEmail("email@test.com");
        //Assertions.assertNotNull(foundUser);

        Assertions.assertEquals("Incorrect password", response.getMessage());

    }

    @Test
    @DisplayName("Login_Successful")
    public void loginSuccessful() throws Exception {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        //test
        LoginRequest request = new LoginRequest("email@test.com", "pass");
        LoginResponse response = service.login(request);

        Assertions.assertEquals("Successfully logged in", response.getMessage());

    }

//===================== RegisterAdmin tests =====================

    @Test
    @DisplayName("If_RegisterAdminRequest_Is_Null")
    public void registerAdminRequestNull() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(null));
    }

    @Test
    @DisplayName("If_RegisterAdminRequest_Contains_Null_Attributes")
    public void registerAdminRequestAtribTest() {
        RequestAdminRequest requestAllNull = new RequestAdminRequest(null,null,null,null,null);
        RequestAdminRequest requestUsernameNull = new RequestAdminRequest("username", "first" , "last", "pass", "email@test.com");
        RequestAdminRequest requestFirstNameNull = new RequestAdminRequest("username", null , "last", "pass", "email@test.com");
        RequestAdminRequest requestLastNameNull = new RequestAdminRequest("username", "first" , null, "pass", "email@test.com");
        RequestAdminRequest requestPasswordNull = new RequestAdminRequest("username", "first" , "last", null, "email@test.com");
        RequestAdminRequest requestEmailNull = new RequestAdminRequest("username", "first" , "last", "pass", null);

        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestAllNull));
    }

//===================== verifyAccount tests =====================

    @Test
    @DisplayName("If_User_Does_Not_Exist")
    public void verifyUserDoesNotExist() throws Exception {
//        VerifyAccountRequest request = new VerifyAccountRequest("missingEmail@notexist.com");
//        VerifyAccountResponse response = service.verifyAccount(request);
//
//        Optional<User> foundUser = verify(userRepository).findUserByEmail("missingEmail@notexist.com");
//        Assertions.assertNull(foundUser);
//
//        Assertions.assertEquals("User does not exist", response.getMessage());
    }

    @Test
    @DisplayName("If_User_Already_Verified")
    public void userAlreadyVerified() throws Exception {
//        VerifyAccountRequest request = new VerifyAccountRequest("missingEmail@notexist.com");
//        VerifyAccountResponse response = service.verifyAccount(request);
//
//        Optional<User> foundUser = verify(userRepository).findUserByEmail("missingEmail@notexist.com");
//        Assertions.assertNull(foundUser);
//
//        Assertions.assertEquals("This account has already been verified", response.getMessage());
    }
}