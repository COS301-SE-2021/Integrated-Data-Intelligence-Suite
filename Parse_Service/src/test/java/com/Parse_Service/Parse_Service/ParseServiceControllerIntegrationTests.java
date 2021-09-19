package com.Parse_Service.Parse_Service;

import com.Parse_Service.Parse_Service.controller.ParseServiceController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParseServiceControllerIntegrationTests {
    @Autowired
    private ParseServiceController parseServiceController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    HttpHeaders requestHeaders = new HttpHeaders();

    @Test
    @Order(1)
    @DisplayName("Test_To_Ensure_User_Service_Controller_Loads")
    public void testControllerNotNull() {
        Assertions.assertNotNull(parseServiceController);
    }
}
