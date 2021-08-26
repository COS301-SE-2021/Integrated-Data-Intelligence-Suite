package com.User_Service.User_Service.service;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.exception.InvalidRequestException;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import com.User_Service.User_Service.rri.Permission;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

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
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test
        ManagePermissionsRequest request = new ManagePermissionsRequest("UserNameTest", Permission.IMPORTING);
        ManagePersmissionsResponse response = service.managePermissions(request);

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(testUser));
        Optional<User> foundUser = verify(userRepository).findUserByUsername("UserNameTest");
        Assertions.assertNotNull(foundUser);

        int count = verify(userRepository).updatePermission(foundUser.get().getId(), foundUser.get().getPermission());
        Assertions.assertNotEquals(0,count);

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
        ManagePermissionsRequest request = new ManagePermissionsRequest("NotUserNameTest", Permission.VIEWING);
        ManagePersmissionsResponse response = service.managePermissions(request);

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

        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test

        RegisterRequest request = new RegisterRequest("UserNameTest", "FirstNameTestDifferent", "LastNameTestDifferent", "emailDifferent@test.com", "passwordTestDifferent");
        RegisterResponse response = service.register(request);

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(testUser));
        Optional<User> foundUser = verify(userRepository).findUserByUsername("UserNameTest");
        Assertions.assertNotNull(foundUser);

        Assertions.assertEquals("Username has been taken", response.getMessage());
    }

    @Test
    @DisplayName("If_New_User_Email_Already_Taken")
    public void registerEmailTaken() throws Exception {
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test
        RegisterRequest request = new RegisterRequest("UserNameTestDifferent", "FirstNameTestDifferent", "LastNameTestDifferent", "email@test.com", "passwordTestDifferent");
        RegisterResponse response = service.register(request);

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(testUser));
        Optional<User> foundUser = verify(userRepository).findUserByUsername("UserNameTest");
        Assertions.assertNotNull(foundUser);

        Assertions.assertEquals("This email has already been registered", response.getMessage());
    }

    @Test
    @DisplayName("User_Successfully_Registered")
    public void registerSuccessful() throws Exception {
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.VIEWING);

        //test
        RegisterRequest request = new RegisterRequest(
                testUser.getUsername(),
                testUser.getFirstName(),
                testUser.getLastName(),
                testUser.getPassword(),
                testUser.getEmail());
        RegisterResponse response = service.register(request);

        /*String password = request.getPassword();
        String hashedPass;
        //Hashing the password
        int iterations = 1000;
        char[] chars = password.toCharArray();
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        hashedPass = iterations + ":" + toHex(salt) + ":" + toHex(hash);*/

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User testUser2 = userArgumentCaptor.getValue();

        Assertions.assertEquals(testUser,testUser2);
        Assertions.assertEquals("Registration successful", response.getMessage());
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
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test

        LoginRequest request = new LoginRequest("email@test.com", "wrongPasswordTest");
        LoginResponse response = service.login(request);

        Optional<User> foundUser = verify(userRepository).findUserByEmail("email@test.com");
        Assertions.assertNotNull(foundUser);

        Assertions.assertEquals("Incorrect password", response.getMessage());

    }

    @Test
    @DisplayName("Login_Successful")
    public void loginSuccessful() throws Exception {
        User testUser = new User();

        testUser.setFirstName("FirstNameTest");
        testUser.setLastName("LastNameTest");
        testUser.setUsername("UserNameTest");
        testUser.setEmail("email@test.com");
        testUser.setPassword("passwordTest");
        testUser.setPermission(Permission.IMPORTING);

        userRepository.save(testUser);

        //test

        LoginRequest request = new LoginRequest("email@test.com", "passwordTest");
        LoginResponse response = service.login(request);

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(testUser));
        Optional<User> foundUser = verify(userRepository).findUserByEmail("email@test.com");
        Assertions.assertNotNull(foundUser);

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
        RegisterAdminRequest requestAllNull = new RegisterAdminRequest(null,null,null,null,null);
        RegisterAdminRequest requestUsernameNull = new RegisterAdminRequest("username", "first" , "last", "pass", "email@test.com");
        RegisterAdminRequest requestFirstNameNull = new RegisterAdminRequest("username", null , "last", "pass", "email@test.com");
        RegisterAdminRequest requestLastNameNull = new RegisterAdminRequest("username", "first" , null, "pass", "email@test.com");
        RegisterAdminRequest requestPasswordNull = new RegisterAdminRequest("username", "first" , "last", null, "email@test.com");
        RegisterAdminRequest requestEmailNull = new RegisterAdminRequest("username", "first" , "last", "pass", null);

        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestAllNull));
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestUsernameNull));
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestFirstNameNull));
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestLastNameNull));
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestPasswordNull));
        Assertions.assertThrows(InvalidRequestException.class, () -> service.requestAdmin(requestEmailNull));
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