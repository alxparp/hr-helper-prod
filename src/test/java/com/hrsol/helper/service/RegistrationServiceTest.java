package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.UserConverter;
import com.hrsol.helper.entity.ConfirmationToken;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.NotificationRequest;
import com.hrsol.helper.model.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private EmailService emailService;
    private RegistrationService registrationService;
    private User user;
    private ConfirmationToken confirmationToken;
    private NotificationRequest notification;
    private String token;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationService(userService, confirmationTokenService, emailService);
        user = DummyObjects.getUser();
        confirmationToken = DummyObjects.getConfirmationToken();
        notification = DummyObjects.getNotification();
        token = confirmationToken.getToken();
    }

    @Test
    void register() {
        // given
        UserDTO userDTO = UserConverter.userToUserDTO(user);
        when(userService.saveUser(userDTO)).thenReturn(user);
        when(confirmationTokenService.saveConfirmationToken(any(ConfirmationToken.class))).thenReturn(confirmationToken);

        // when
        registrationService.register(userDTO);
        NotificationRequest notificationActual = getCapturedNotification();

        // then
        Assertions.assertEquals(notification.getFrom(), notificationActual.getFrom());
        Assertions.assertEquals(notification.getTo(), notificationActual.getTo());
        Assertions.assertEquals(notification.getSubject(), notificationActual.getSubject());
    }

    private NotificationRequest getCapturedNotification() {
        ArgumentCaptor<NotificationRequest> notificationArgumentCaptor =
                ArgumentCaptor.forClass(NotificationRequest.class);
        verify(emailService).send(notificationArgumentCaptor.capture());

        return notificationArgumentCaptor.getValue();
    }

    @Test
    void confirmToken() {
        // given
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
        doNothing().when(confirmationTokenService).setConfirmedAt(token);
        doNothing().when(userService).enableUser(confirmationToken.getUsername());

        // when
        String confirmation = registrationService.confirmToken(token);

        // then
        Assertions.assertEquals("confirmed", confirmation);
    }

    @Test
    void confirmToken_NullToken() {
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            registrationService.confirmToken(token);
        });

    }

    @Test
    void confirmToken_AlreadyConfirmed() {
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        assertThrows(IllegalStateException.class, () -> {
            registrationService.confirmToken(token);
        });

    }

    @Test
    void confirmToken_Expired() {
        when(confirmationTokenService.getToken(token)).thenReturn(Optional.of(confirmationToken));
        confirmationToken.setExpiresAt(LocalDateTime.now());

        assertThrows(IllegalStateException.class, () -> {
            registrationService.confirmToken(token);
        });

    }
}