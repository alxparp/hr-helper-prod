package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.UserConverter;
import com.hrsol.helper.entity.*;
import com.hrsol.helper.entity.enums.UserStatusType;
import com.hrsol.helper.model.UserDTO;
import com.hrsol.helper.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private LocationService locationService;
    @Mock
    private UserStatusService userStatusService;
    private UserService userService;
    private User user;
    private Role role;
    private Location location;
    private UserStatus userStatus;

    @BeforeEach
    void setUp() {
        userService = new UserService(
                userRepository,
                passwordEncoder,
                locationService,
                roleService,
                userStatusService);
        user = DummyObjects.getUser();
        role = DummyObjects.getRole();
        location = DummyObjects.getLocation();
        userStatus = DummyObjects.getUserStatus();
    }

    @Test
    void findAllUsers() {
        // given
        List<UserDTO> userDTOSExpected = List.of(UserConverter.userToUserDTO(user));
        when(userRepository.findAll()).thenReturn(List.of(user));

        // when
        List<UserDTO> userDTOSActual = userService.findAllUsers();

        // then
        Assertions.assertEquals(userDTOSExpected, userDTOSActual);
    }

    @Test
    void findByUsername() {
        // given
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // when
        User actualUser = userService.findByUsername(user.getUsername());

        // then
        Assertions.assertEquals(user, actualUser);
    }

    @Test
    void saveUser() {
        // given
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(roleService.findByRoleName(role.getName())).thenReturn(role);
        when(locationService.getLocationByCity(user.getLocation().getCity())).thenReturn(location);
        when(userStatusService.getUserStatusByType(UserStatusType.AVAILABLE)).thenReturn(userStatus);
        when(userRepository.save(user)).thenReturn(user);

        // when
        User actualUser = userService.saveUser(UserConverter.userToUserDTO(user));

        // then
        Assertions.assertEquals(user, actualUser);

    }

    @Test
    void enableUser() {
        // given
        doNothing().when(userRepository).enableUser(user.getUsername());

        // when
        userService.enableUser(user.getUsername());

        // then
        verify(userRepository, times(1)).enableUser(user.getUsername());


    }
}