package com.hrsol.helper.service.impl;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.entity.ConfirmationToken;
import com.hrsol.helper.repository.ConfirmationTokenRepository;
import com.hrsol.helper.service.impl.ConfirmationTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    private ConfirmationTokenService confirmationTokenService;
    private ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
        confirmationToken = DummyObjects.getConfirmationToken();
    }

    @Test
    void saveConfirmationToken() {
        // given
        when(confirmationTokenRepository.save(confirmationToken)).thenReturn(confirmationToken);

        // when
        ConfirmationToken confirmationTokenActual = confirmationTokenService.saveConfirmationToken(confirmationToken);

        // then
        Assertions.assertEquals(confirmationToken, confirmationTokenActual);
    }

    @Test
    void getToken() {
        // given
        Optional<ConfirmationToken> confirmationTokenExpected = Optional.of(confirmationToken);
        when(confirmationTokenRepository.findByToken(confirmationToken.getToken())).thenReturn(confirmationTokenExpected);

        // when
        Optional<ConfirmationToken> confirmationTokenActual = confirmationTokenService.getToken(confirmationToken.getToken());

        // then
        Assertions.assertEquals(confirmationTokenExpected, confirmationTokenActual);
    }

    @Test
    void setConfirmedAt() {
        try(MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class)) {
            LocalDateTime confirmedAt = confirmationToken.getConfirmedAt();
            String token = confirmationToken.getToken();
            localDateTimeMockedStatic.when(LocalDateTime::now).thenReturn(confirmedAt);
            doNothing().when(confirmationTokenRepository).updateConfirmedAt(token, confirmedAt);

            // when
            confirmationTokenService.setConfirmedAt(token);

            // then
            verify(confirmationTokenRepository, times(1)).updateConfirmedAt(token, confirmedAt);
        }
    }
}