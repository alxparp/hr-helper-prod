package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(userRepository);
        userDetails = DummyObjects.getUserDetails();
        user = DummyObjects.getUser();
    }

    @Test
    void loadUserByUsername() {
        // given
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        // when
        UserDetails userDetailsActual = customUserDetailsService.loadUserByUsername(user.getUsername());

        // then
        Assertions.assertEquals(userDetails, userDetailsActual);
    }
}