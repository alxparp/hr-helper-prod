package com.hrsol.helper.repository;

import com.hrsol.helper.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    UserStatus findByType(String type);

}
