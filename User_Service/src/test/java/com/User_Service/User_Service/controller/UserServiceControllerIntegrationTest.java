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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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

        Assertions.assertFalse(responseEntity.getBody().isSuccess());
        Assertions.assertEquals("Username has been taken", responseEntity.getBody().getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test_Verify_User_Successful")
    public void verifySuccessful() {
        Optional<User> user = userRepository.findUserByEmail(testUser1.getEmail());
        System.out.println("Verifying...");
        if(user.isPresent()) {
            VerifyAccountRequest request = new VerifyAccountRequest(testUser1.getEmail(), user.get().getVerificationCode());

            HttpEntity<VerifyAccountRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<VerifyAccountResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/verifyAccount", HttpMethod.POST, requestEntity, VerifyAccountResponse.class);

            Assertions.assertTrue(responseEntity.getBody().isSuccess());
            Assertions.assertEquals("Successfully verified account", responseEntity.getBody().getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test_Verify_User_Unsuccessful")
    public void verifyUnsuccessful() {
        Optional<User> user = userRepository.findUserByEmail(testUser1.getEmail());
        if(user.isPresent()) {
            VerifyAccountRequest request = new VerifyAccountRequest(testUser1.getEmail(), user.get().getEmail());

            HttpEntity<VerifyAccountRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
            ResponseEntity<VerifyAccountResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/User/verifyAccount", HttpMethod.POST, requestEntity, VerifyAccountResponse.class);

            Assertions.assertFalse(responseEntity.getBody().isSuccess());
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
