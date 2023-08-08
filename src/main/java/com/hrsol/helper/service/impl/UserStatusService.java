package com.hrsol.helper.service.impl;

import com.hrsol.helper.entity.UserStatus;
import com.hrsol.helper.entity.enums.UserStatusType;
import com.hrsol.helper.repository.UserStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;

    public UserStatusService(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    public UserStatus getUserStatusByType(UserStatusType statusType) {
        return userStatusRepository.findByType(statusType.name());
    }
}
