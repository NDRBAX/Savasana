package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerIntegrationTest {

    private final AuthController authController;

    @Autowired
    public AuthControllerIntegrationTest(AuthController authController) {
        this.authController = authController;
    }

    private static final String EMAIL = "test_auth_controller@test.com";
    private static final String PASSWORD = "password123";
    private static final String FIRSTNAME = "André";
    private static final String LASTNAME = "Test";

    @Test
    @Order(1)
    @DisplayName("Test the registerUser method")
    public void registerUser() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(EMAIL);
        signupRequest.setFirstName(FIRSTNAME);
        signupRequest.setLastName(LASTNAME);
        signupRequest.setPassword(PASSWORD);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Test to register a user with an existing email")
    public void registerUserWithExistingEmail() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(EMAIL);
        signupRequest.setFirstName(FIRSTNAME);
        signupRequest.setLastName(LASTNAME);
        signupRequest.setPassword(PASSWORD);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @Order(3)
    @DisplayName("Test the authenticateUser method with invalid credentials")
    public void authenticateUserWithInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword("wrongPassword");

        assertThrows(BadCredentialsException.class, () -> {
            ResponseEntity<?> response = authController.authenticateUser(loginRequest);
            assertEquals(400, response.getStatusCodeValue());
            assertEquals("Error: Email is already taken!", ((MessageResponse) response.getBody()).getMessage());
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test the authenticateUser method with valid credentials")
    public void authenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }

}