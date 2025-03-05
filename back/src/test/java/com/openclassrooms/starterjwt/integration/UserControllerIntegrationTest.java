package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mocks.UserMocks;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {
    private UserController userController;
    private final AuthController authController;
    private final UserMocks userMocks = new UserMocks();

    @Autowired
    public UserControllerIntegrationTest(UserController userController, AuthController authController) {
        this.userController = userController;
        this.authController = authController;
    }

    private static final String EMAIL = "test_user_controller@test.com";
    private static final String PASSWORD = "password123";
    private static final String FIRSTNAME = "André";
    private static final String LASTNAME = "Test";

    private JwtResponse authenticatedUser;

    @BeforeEach 
    public void setUp() {
        SignupRequest signupRequest = userMocks.createSignupRequest(EMAIL, LASTNAME, FIRSTNAME, PASSWORD);

        ResponseEntity<?> RegisterResponse = authController.registerUser(signupRequest);

        assertNotNull(RegisterResponse);
        assertEquals(200, RegisterResponse.getStatusCodeValue());

        LoginRequest loginRequest = userMocks.createLoginRequest(EMAIL, PASSWORD);

        ResponseEntity<?> loginResponse = authController.authenticateUser(loginRequest);
        assertNotNull(loginResponse);
        assertEquals(200, loginResponse.getStatusCodeValue());

        authenticatedUser = ((JwtResponse) loginResponse.getBody());
        assertNotNull(authenticatedUser);
        assertNotNull(authenticatedUser.getToken());
        assertNotNull(authenticatedUser.getId());

    }


    @Test
    @Order(1)
    @DisplayName("Delete authenticated user")
    public void deleteAuthenticatedUser() {
        assertNotNull(authenticatedUser);
        assertNotNull(authenticatedUser.getId());

        ResponseEntity<?> response = userController.save(authenticatedUser.getId().toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Order(2)   
    @DisplayName("Get authenticated user")
    public void getAuthenticatedUser() {
        assertNotNull(authenticatedUser);
        assertNotNull(authenticatedUser.getId());

        ResponseEntity<?> response = userController.findById(authenticatedUser.getId().toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserDto user = (UserDto) response.getBody();
        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(FIRSTNAME, user.getFirstName());
        assertEquals(LASTNAME, user.getLastName());
    }


}
