//import com.User_Service.User_Service.dataclass.User;
//import com.User_Service.User_Service.exception.InvalidRequestException;
//import com.User_Service.User_Service.repository.UserRepository;
//import com.User_Service.User_Service.request.*;
//import com.User_Service.User_Service.response.*;
//import com.User_Service.User_Service.rri.Permission;
//import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserServiceImpl service;
//
//    @Mock
//    private UserRepository repository;
//
//    @Before
//    public void initMocks() {
//        MockitoAnnotations.initMocks(this);
//    }
//    /*
//    ============================ManagePermissions tests============================
//    */
//
//    @Test
//    @DisplayName("If_ManagePermissionsRequest_Is_Null")
//    public void managePermissionsNullRequest() {
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(null));
//    }
//
//    @Test
//    @DisplayName("If_Both_ManagePermissionsRequest_Attributes_Are_Null")
//    public void managePermissionsRequestNullAttribs() {
//        ManagePermissionsRequest request = new ManagePermissionsRequest(null, null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
//    }
//
//    @Test
//    @DisplayName("If_ManagePermissionsRequest_Username_Field_Is_Null")
//    public void managePermissionsRequestNullUsernameField() {
//        ManagePermissionsRequest request = new ManagePermissionsRequest(null, Permission.VIEWING);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
//    }
//
//    @Test
//    @DisplayName("If_ManagePermissionsRequest_Permission_Field_Is_Null")
//    public void managePermissionsRequestNullPermissionField() {
//        ManagePermissionsRequest request = new ManagePermissionsRequest("exampleUser", null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.managePermissions(request));
//    }
//
//    @Test
//    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Exists")
//    public void managePermissionsValidRequestUserExists() throws Exception {
//        ManagePermissionsRequest request = new ManagePermissionsRequest("testUser", Permission.VIEWING);
//        String expectedMessage = "Permission updated";
//        ManagePersmissionsResponse response = service.managePermissions(request);
//        Assertions.assertEquals(response.getMessage(), expectedMessage);
//    }
//
//    @Test
//    @DisplayName("If_ManagePermissionsRequest_Is_Valid_And_User_Does_Not_Exist")
//    public void managePermissionsValidRequestUserNotExists() throws Exception {
//        ManagePermissionsRequest request = new ManagePermissionsRequest("nonExistantUser", Permission.VIEWING);
//        String expectedMessage = "User does not exist";
//        ManagePersmissionsResponse response = service.managePermissions(request);
//        Assertions.assertEquals(response.getMessage(), expectedMessage);
//    }
//
//    /*
//    ============================Register tests============================
//    */
//
//    /*
//    Saving using repository only
//     */
//
//    @Test
//    @DisplayName("Saving_Repository_Test")
//    public void savingRepository() {
//        User user = new User("testuser", "testUserlast", "usertestname", "test22@gmail.com", "password", Permission.VIEWING);
//
//        repository.save(user);
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_Is_Null")
//    public void registerNullRequest() {
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(null));
//    }
//
//    @Test
//    @DisplayName("If_All_RegisterRequest_Attributes_Are_Null")
//    public void registerRequestAllAttribNull() {
//        RegisterRequest request = new RegisterRequest(null, null, null, null, null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_Username_Field_Is_Null")
//    public void registerRequestUsernameNull() {
//        RegisterRequest request = new RegisterRequest(null, "firstname", "lastname", "password", "email");
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_FirstName_Field_Is_Null")
//    public void registerRequestFirstNameNull() {
//        RegisterRequest request = new RegisterRequest("username", null, "lastname", "password", "email");
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_LastName_Field_Is_Null")
//    public void registerRequestLastNameNull() {
//        RegisterRequest request = new RegisterRequest("username", "firstname", null, "password", "email");
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_Password_Field_Is_Null")
//    public void registerRequestPasswordNull() {
//        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", null, "email");
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_RegisterRequest_Email_Field_Is_Null")
//    public void registerRequestEmailNull() {
//        RegisterRequest request = new RegisterRequest("username", "firstname", "lastname", "password", null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.register(request));
//    }
//
//    @Test
//    @DisplayName("If_New_User_Username_Already_Taken")
//    public void registerUsernameTaken() throws Exception {
//        RegisterRequest request = new RegisterRequest("takenTest1", "firstname", "lastname", "password", "email");
//        String expectedMessage = "Username has been taken";
//        RegisterResponse response = service.register(request);
//        Assertions.assertEquals(response.getMessage(), expectedMessage);
//    }
//
//    @Test
//    @DisplayName("If_New_User_Email_Already_Taken")
//    public void registerEmailTaken() throws Exception {
//        RegisterRequest request = new RegisterRequest("newUser3", "firstname", "lastname", "password", "takenEmail@gmail.com");
//        String expectedMessage = "Email has been taken";
//        RegisterResponse response = service.register(request);
//        Assertions.assertEquals(expectedMessage, response.getMessage());
//    }
//
//    @Test
//    @DisplayName("User_Successfully_Registered")
//    public void registerSuccessful() throws Exception {
//        RegisterRequest request = new RegisterRequest("newUser", "firstname", "lastname", "password", "newEmail");
//        String expectedMessage = "Registration successful";
//        RegisterResponse response = service.register(request);
//        Assertions.assertEquals(response.getMessage(), expectedMessage);
//    }
//
//    @Test
//    @DisplayName("If_LoginRequest_Is_Null")
//    public void loginRequestNull() {
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(null));
//    }
//
//    @Test
//    @DisplayName("If_LoginRequest_All_Attrib_Are_Null")
//    public void loginRequestAttribAllNull() {
//        LoginRequest request = new LoginRequest(null, null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
//    }
//
//    @Test
//    @DisplayName("If_LoginRequest_Email_Null")
//    public void loginRequestEmailNull() {
//        LoginRequest request = new LoginRequest(null, "password");
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
//    }
//
//    @Test
//    @DisplayName("If_LoginRequest_Password_Null")
//    public void loginRequestPasswordNull() {
//        LoginRequest request = new LoginRequest("test@email.com", null);
//        Assertions.assertThrows(InvalidRequestException.class, () -> service.login(request));
//    }
//
//    @Test
//    @DisplayName("Login_If_Email_Does_Not_Exist")
//    public void loginEmailNotExist() throws Exception {
//        LoginRequest request = new LoginRequest("missingEmail@notexist.com", "password");
//        String expected = "The email does not exist";
//        LoginResponse response = service.login(request);
//        Assertions.assertEquals(expected, response.getMessage());
//    }
//
//    @Test
//    @DisplayName("Login_Existing_Email_Wrong_Password")
//    public void loginWrongPassword() throws Exception {
//        LoginRequest request = new LoginRequest("existingEmail@exist.com", "wrongpass");
//        String expected = "Incorrect password";
//        LoginResponse response = service.login(request);
//        Assertions.assertEquals(response.getMessage(), expected);
//    }
//
//    @Test
//    @DisplayName("Login_Successful")
//    public void loginSuccessful() throws Exception {
//        LoginRequest request = new LoginRequest("existingEmail@exist.com", "correctpass");
//        String expected = "Successfully logged in";
//        LoginResponse response = service.login(request);
//        Assertions.assertEquals(response.getMessage(), expected);
//    }
//}
