package com.hrsol.helper.service;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.entity.UserStatus;
import com.hrsol.helper.repository.UserStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hrsol.helper.entity.UserStatusType.AVAILABLE;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStatusServiceTest {

    @Mock
    private UserStatusRepository userStatusRepository;
    private UserStatusService userStatusService;
    private UserStatus userStatus;

    @BeforeEach
    void setUp() {
        userStatusService = new UserStatusService(userStatusRepository);
        userStatus = DummyObjects.getUserStatus();
    }

    @Test
    void getUserStatusByType() {
        // given
        when(userStatusRepository.findByType(userStatus.getType())).thenReturn(userStatus);

        // when
        UserStatus userStatusActual = userStatusService.getUserStatusByType(AVAILABLE);

        // then
        Assertions.assertEquals(userStatus, userStatusActual);
    }
}