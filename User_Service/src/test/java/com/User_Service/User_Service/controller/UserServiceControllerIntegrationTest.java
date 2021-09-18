package com.User_Service.User_Service.controller;

import com.User_Service.User_Service.dataclass.User;
import com.User_Service.User_Service.repository.UserRepository;
import com.User_Service.User_Service.rri.Permission;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import com.User_Service.User_Service.request.*;
import com.User_Service.User_Service.response.*;
import org.springframework.http.*;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceControllerIntegrationTest {
    @Autowired
    private UserServiceController userServiceController;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    User testUser1 = new User("firstname", "lastname", "username","email@email.com1", "password", Permission.VIEWING);

    HttpHeaders requestHeaders = new HttpHeaders();

    @Test
    @Order(1)
    @DisplayName("Test_To_Ensure_User_Service_Controller_Loads")
    public void testControllerNotNull() {
        Assertions.assertNotNull(userServiceController);
    }

    @Test
    @Order(2)
    @DisplayName("Test_Registration_Is_Successful")
    public void successfulRegister() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        RegisterRequest request = new RegisterRequest(testUser1.getUsername(), testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), testUser1.getEmail());

        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<RegisterResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/register", HttpMethod.POST, requestEntity, RegisterResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("Registration successful. An email will be sent shortly containing your registration details.", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test_Registration_Unsuccessful")
    public void unsuccessfulRegister() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        RegisterRequest request = new RegisterRequest(testUser1.getUsername(), testUser1.getFirstName(), testUser1.getLastName(), testUser1.getPassword(), testUser1.getEmail());

        HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<RegisterResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/register", HttpMethod.POST, requestEntity, RegisterResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertFalse(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("Username has been taken", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test_Login_Failed_Account_Not_Verified")
    public void loginFailedNotVerified() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest request = new LoginRequest(testUser1.getEmail(), testUser1.getPassword());

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<LoginResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/login", HttpMethod.POST, requestEntity, LoginResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("This account is not verified. Please verify to get access to the system", responseEntity.getBody().getMessage());
        Assertions.assertFalse(responseEntity.getBody().isSuccess());
    }

    @Test
    @Order(5)
    @DisplayName("Test_Verify_User_Successful")
    public void verifySuccessful() {
        Optional<User> user = userRepository.findUserByEmail(testUser1.getEmail());

        if(user.isPresent()) {
            System.out.println("Verifying...");
            VerifyAccountRequest request = new VerifyAccountRequest(testUser1.getEmail(), user.get().getVerificationCode());

            HttpEntity<VerifyAccountRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<VerifyAccountResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/verifyAccount", HttpMethod.POST, requestEntity, VerifyAccountResponse.class);

            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertTrue(responseEntity.getBody().isSuccess());
            Assertions.assertEquals("Successfully verified account", responseEntity.getBody().getMessage());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test_Verify_User_Unsuccessful")
    public void verifyUnsuccessful() {
        Optional<User> user = userRepository.findUserByEmail(testUser1.getEmail());
        if(user.isPresent()) {
            VerifyAccountRequest request = new VerifyAccountRequest(testUser1.getEmail(), user.get().getEmail());

            HttpEntity<VerifyAccountRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<VerifyAccountResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/verifyAccount", HttpMethod.POST, requestEntity, VerifyAccountResponse.class);

            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertFalse(responseEntity.getBody().isSuccess());
        }
    }


    @Test
    @Order(7)
    @DisplayName("Test_Login_Successful")
    public void loginSuccessful() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest request = new LoginRequest(testUser1.getEmail(), testUser1.getPassword());

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<LoginResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/login", HttpMethod.POST, requestEntity, LoginResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Successfully logged in", responseEntity.getBody().getMessage());
        Assertions.assertTrue(responseEntity.getBody().isSuccess());
    }

    @Test
    @Order(8)
    @DisplayName("Test_Login_Failed_Incorrect_Password")
    public void loginFailedIncorrectPass() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest request = new LoginRequest(testUser1.getEmail(), "wrongpass");

        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<LoginResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/login", HttpMethod.POST, requestEntity, LoginResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertFalse(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("Incorrect password", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Test_Change_Permission_To_IMPORTING_Successful")
    public void changePermissionSuccessful() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ManagePermissionsRequest request = new ManagePermissionsRequest(testUser1.getUsername(), Permission.IMPORTING);

        HttpEntity<ManagePermissionsRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ManagePersmissionsResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/changepermission", HttpMethod.POST, requestEntity, ManagePersmissionsResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("Permission updated", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(10)
    @DisplayName("Test_Change_Permission_To_IMPORTING_Unsuccessful")
    public void changePermissionUnsuccessful() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ManagePermissionsRequest request = new ManagePermissionsRequest("nonexistingUser", Permission.IMPORTING);

        HttpEntity<ManagePermissionsRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ManagePersmissionsResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/changepermission", HttpMethod.POST, requestEntity, ManagePersmissionsResponse.class);

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertFalse(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("User does not exist", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(11)
    @DisplayName("Test_Get_Current_User_Successful")
    public void getCurrentUserSuccessful() {
        Optional<User> currentUser = userRepository.findUserByEmail(testUser1.getEmail());
        if(currentUser.isPresent()) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            GetCurrentUserRequest request = new GetCurrentUserRequest(currentUser.get().getId().toString());

            HttpEntity<GetCurrentUserRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<GetCurrentUserResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/getCurrentUser", HttpMethod.POST, requestEntity, GetCurrentUserResponse.class);

            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertTrue(responseEntity.getBody().getSuccess());
            Assertions.assertEquals("Successfully returned current user", responseEntity.getBody().getMessage());
            Assertions.assertEquals(testUser1.getUsername(), responseEntity.getBody().getUsername());
        }
    }

    @Test
    @Order(12)
    @DisplayName("Test_Get_Current_User_Failed_Does_Not_Exist")
    public void getCurrentUserFailed() {
        Optional<User> currentUser = userRepository.findUserByEmail(testUser1.getEmail());
        if(currentUser.isPresent()) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            GetCurrentUserRequest request = new GetCurrentUserRequest("1e121c9e-11f1-4378-975f-7bb769e52ac3");

            HttpEntity<GetCurrentUserRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<GetCurrentUserResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/getCurrentUser", HttpMethod.POST, requestEntity, GetCurrentUserResponse.class);

            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertFalse(responseEntity.getBody().getSuccess());
            Assertions.assertEquals("User does not exist", responseEntity.getBody().getMessage());
        }
    }

    @Test
    @Order(13)
    @DisplayName("Test_Get_All_Users_Successful")
    public void getAllUsersSuccessful() {
        Optional<User> currentUser = userRepository.findUserByEmail(testUser1.getEmail());
        if(currentUser.isPresent()) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> requestEntity = new HttpEntity<>(requestHeaders);
            ResponseEntity<GetAllUsersResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/getAll", HttpMethod.GET, requestEntity, GetAllUsersResponse.class);

            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertTrue(responseEntity.getBody().isSuccess());
            Assertions.assertEquals("Returned list of users", responseEntity.getBody().getMessage());
            Assertions.assertNotNull(responseEntity.getBody().getUsers());
        }
    }


    @Test
    @Order(99)
    @DisplayName("Delete_testUser1")
    public void removeMockUser() {
        Optional<User> user = userRepository.findUserByEmail(testUser1.getEmail());
        user.ifPresent(value -> userRepository.delete(value));
    }
}
