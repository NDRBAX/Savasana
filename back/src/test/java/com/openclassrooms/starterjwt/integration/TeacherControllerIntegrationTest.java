package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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
import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mocks.UserMocks;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherControllerIntegrationTest {
    private TeacherController teacherController;
    private final AuthController authController;
    private final UserMocks userMocks = new UserMocks();

    @Autowired
    public TeacherControllerIntegrationTest(TeacherController teacherController, AuthController authController) {
        this.teacherController = teacherController;
        this.authController = authController;
    }

    private static final String EMAIL = "test_teacher_controller@test.com";
    private static final String PASSWORD = "password123";
    private static final String FIRSTNAME = "André";
    private static final String LASTNAME = "Test";

    List<TeacherDto> teachers;

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

    }


    @Test
    @Order(1)
    @DisplayName("Test findAll Success")
    public void testFindAllSuccess() {
        ResponseEntity<?> response = teacherController.findAll();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        teachers = (List<TeacherDto>) response.getBody();
        assertNotNull(teachers);
        assertEquals(2, teachers.size());
    }

    @Test
    @Order(2)
    @DisplayName("Test findById Success")
    public void testFindByIdSuccess() {
        assertNotNull(teachers);
        TeacherDto teacher = teachers.get(0);
        System.out.println("######### " + teacher.getId());
        ResponseEntity<?> response = teacherController.findById(teacher.getId().toString());

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        TeacherDto teacherResponse = (TeacherDto) response.getBody();
        assertNotNull(teacherResponse);
        assertEquals(teacher.getId(), teacherResponse.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Test findById Failure")
    public void testFindByIdFailure() {
        ResponseEntity<?> response = teacherController.findById("999999");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


}
