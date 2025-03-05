package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;


@SpringBootTest
public class UserDetailsImplTest {

    @Test
    @DisplayName("Builder should create correct object")
    public void builder_ShouldCreateCorrectObject() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("test@example.com")
                .firstName("Test")
                .lastName("User")
                .password("password")
                .admin(true)
                .build();

        assertEquals(1L, userDetails.getId());
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("Test", userDetails.getFirstName());
        assertEquals("User", userDetails.getLastName());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAdmin());
    }

    @Test
    @DisplayName("getAuthorities should return an empty collecrtion")
    public void getAuthorities_ShouldReturnEmptyCollection() {
        UserDetailsImpl userDetails = createTestUserDetails();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    @DisplayName("isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled should always return true")
    public void accountStateMethods_ShouldAlwaysReturnTrue() {
        UserDetailsImpl userDetails = createTestUserDetails();

        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    @DisplayName("equals should return true for objects with the same ID")
    public void equals_WithSameId_ShouldReturnTrue() {
        UserDetailsImpl userDetails1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl userDetails2 = UserDetailsImpl.builder().id(1L).build();

        assertEquals(userDetails1, userDetails2);
    }

    @Test
    @DisplayName("equals should return false for objects with different IDs")
    public void equals_WithDifferentIds_ShouldReturnFalse() {
        UserDetailsImpl userDetails1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl userDetails2 = UserDetailsImpl.builder().id(2L).build();

        assertNotEquals(userDetails1, userDetails2);
    }

    @Test
    @DisplayName("equals should return false for null or different type")
    public void equals_WithNullOrDifferentType_ShouldReturnFalse() {
        UserDetailsImpl userDetails = createTestUserDetails();
        assertNotEquals(userDetails, null);
        assertNotEquals(userDetails, new Object());
    }

    private UserDetailsImpl createTestUserDetails() {
        return UserDetailsImpl.builder()
                .id(1L)
                .username("test@example.com")
                .firstName("Test")
                .lastName("User")
                .password("password")
                .admin(false)
                .build();
    }
}