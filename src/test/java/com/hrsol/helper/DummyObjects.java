package com.hrsol.helper;

import com.hrsol.helper.entity.*;
import com.hrsol.helper.model.NotificationRequest;
import com.hrsol.helper.util.Util;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.hrsol.helper.entity.enums.UserStatusType.AVAILABLE;

public class DummyObjects {

    public static User getUser() {
        User user = User.builder()
                .firstName("Mykola")
                .lastName("Mykolenko")
                .email("mykola@gmail.com")
                .username("mykola")
                .password(new BCryptPasswordEncoder().encode("password"))
                .hireDate(Date.valueOf(LocalDate.now()))
                .birthDate(Date.valueOf(LocalDate.now()))
                .disabled(true)
                .locked(false)
                .userStatus(getUserStatus())
                .location(getLocation())
                .roles(Set.of(getRole()))
                .build();
        return user;
    }

    public static UserStatus getUserStatus() {
        return UserStatus.builder()
                .id(1L)
                .type(AVAILABLE.name())
                .build();
    }

    public static Location getLocation() {
        return Location.builder()
                .id(1L)
                .city("Odesa")
                .country(getCountry())
                .build();
    }

    public static Country getCountry() {
        return Country.builder()
                .id(1L)
                .country("Ukraine")
                .build();
    }

    public static Role getRole() {
        Role role = Role.builder()
                .roleId(1L)
                .name("ADMIN")
                .permissions(new HashSet<>())
                .build();
        role.setPermissions(Set.of(getPermission()));
        return role;
    }

    public static Permission getPermission() {
        return Permission.builder()
                .permissionId(1L)
                .name("write")
                .build();
    }

    public static ConfirmationToken getConfirmationToken() {
        return ConfirmationToken.builder()
                .id(1L)
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .username(getUser().getUsername())
                .build();
    }

    public static UserDetails getUserDetails() {
        User user = getUser();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + getRole().getName());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .accountLocked(user.isLocked())
                .disabled(user.isDisabled())
                .authorities(Set.of(authority))
                .build();
    }

    public static NotificationRequest getNotification() {
        return new NotificationRequest(
                Util.FROM,
                getUser().getEmail(),
                Util.CONFIRMATION,
                "Hello world!!!"
        );
    }

}
