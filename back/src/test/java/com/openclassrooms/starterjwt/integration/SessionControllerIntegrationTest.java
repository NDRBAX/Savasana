package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mocks.SessionMocks;
import com.openclassrooms.starterjwt.mocks.UserMocks;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SessionControllerIntegrationTest {

    private final SessionController sessionController;
    private final AuthController authController;
    private final TeacherController teacherController;
    private final SessionMocks sessionMocks = new SessionMocks();
    private final UserMocks userMocks = new UserMocks();

    @Autowired
    public SessionControllerIntegrationTest(SessionController sessionController, AuthController authController,
            TeacherController teacherController) {
        this.sessionController = sessionController;
        this.authController = authController;
        this.teacherController = teacherController;
    }

    private static final String EMAIL = "test_session_controller@test.com";
    private static final String PASSWORD = "password123";
    private static final String FIRSTNAME = "André";
    private static final String LASTNAME = "Test";

    private List<TeacherDto> teacherDtos;
    private Long sessionId;
    private Long authenticatedUserId;
    private SessionDto sessionToUpdate;

    @BeforeAll
    public void setUp() {
        SignupRequest signupRequest = userMocks.createSignupRequest(EMAIL, LASTNAME, FIRSTNAME, PASSWORD);

        ResponseEntity<?> RegisterResponse = authController.registerUser(signupRequest);

        assertNotNull(RegisterResponse);
        assertEquals(200, RegisterResponse.getStatusCodeValue());

        LoginRequest loginRequest = userMocks.createLoginRequest(EMAIL, PASSWORD);

        ResponseEntity<?> loginResponse = authController.authenticateUser(loginRequest);
        assertNotNull(loginResponse);
        assertEquals(200, loginResponse.getStatusCodeValue());

        JwtResponse jwtResponse = (JwtResponse) loginResponse.getBody();
        authenticatedUserId = jwtResponse.getId();

        ResponseEntity<?> response = teacherController.findAll();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        teacherDtos = (List<TeacherDto>) response.getBody();
    }

    @Test
    @Order(1)
    @DisplayName("Create a session")
    public void testCreateSession() {
        SessionDto sessionDto = sessionMocks.createSessionDto(1L, null, teacherDtos.get(0).getId(), null, false, false);

        ResponseEntity<?> response = sessionController.create(sessionDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        sessionId = ((SessionDto) response.getBody()).getId();
        assertNotNull(sessionId);
    }

    @Test
    @Order(2)
    @DisplayName("Get a session by id")
    public void testGetSessionById() {
        assertNotNull(sessionId);
        ResponseEntity<?> response = sessionController.findById(sessionId.toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        SessionDto sessionDto = (SessionDto) response.getBody();
        assertNotNull(sessionDto);
        assertEquals(sessionId, sessionDto.getId());
        sessionToUpdate = sessionDto;
    }

    @Test
    @Order(3)
    @DisplayName("Update a session")
    public void testUpdateSession() {
        assertNotNull(sessionId);
        assertNotNull(sessionToUpdate);

        sessionToUpdate.setName("Updated session name");
        sessionToUpdate.setDescription("Updated session description");

        ResponseEntity<?> response = sessionController.update(sessionId.toString(), sessionToUpdate);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        SessionDto updatedSessionDto = (SessionDto) response.getBody();
        assertNotNull(updatedSessionDto);
        assertEquals(sessionToUpdate.getName(), updatedSessionDto.getName());
        assertEquals(sessionToUpdate.getDescription(), updatedSessionDto.getDescription());
    }

    @Test
    @Order(4)
    @DisplayName("Get all sessions for authenticated user")
    public void testGetAllSessions() {
        ResponseEntity<?> response = sessionController.findAll();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, ((List<SessionDto>) response.getBody()).size());
        System.out.println("Get all sessions for authenticated user: " + response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Participate in a session")
    public void testParticipateInSession() {
        assertNotNull(sessionId);
        assertNotNull(authenticatedUserId);

        ResponseEntity<?> response = sessionController.participate(sessionId.toString(),
                authenticatedUserId.toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<?> sessionResponse = sessionController.findById(sessionId.toString());
        assertNotNull(sessionResponse);
        assertEquals(200, sessionResponse.getStatusCodeValue());

        SessionDto sessionDto = (SessionDto) sessionResponse.getBody();
        assertNotNull(sessionDto);
        assertEquals(1, sessionDto.getUsers().size());
        assertEquals(authenticatedUserId, sessionDto.getUsers().get(0));
    }

    @Test
    @Order(6)
    @DisplayName("Unparticipate in a session")
    public void testUnparticipateInSession() {
        assertNotNull(sessionId);
        assertNotNull(authenticatedUserId);

        ResponseEntity<?> response = sessionController.noLongerParticipate(sessionId.toString(),
                authenticatedUserId.toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<?> sessionResponse = sessionController.findById(sessionId.toString());
        assertNotNull(sessionResponse);
        assertEquals(200, sessionResponse.getStatusCodeValue());

        SessionDto sessionDto = (SessionDto) sessionResponse.getBody();
        assertNotNull(sessionDto);
        assertEquals(0, sessionDto.getUsers().size());
    }

    @Test
    @Order(7)
    @DisplayName("Delete a session")
    public void testDeleteSession() {
        assertNotNull(sessionId);

        ResponseEntity<?> response = sessionController.save(sessionId.toString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<?> removedSession = sessionController.findById(sessionId.toString());
        assertEquals(404, removedSession.getStatusCodeValue());
    }

}
