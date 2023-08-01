package com.hrsol.helper.service;

import com.hrsol.helper.entity.Role;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.repository.UserRepository;
import com.hrsol.helper.converter.UserConverter;
import com.hrsol.helper.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static com.hrsol.helper.entity.UserStatusType.AVAILABLE;
import static com.hrsol.helper.security.UserRole.ADMIN;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationService locationService;
    private final RoleService roleService;
    private final UserStatusService userStatusService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LocationService locationService,
                       RoleService roleService,
                       UserStatusService userStatusService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationService = locationService;
        this.roleService = roleService;
        this.userStatusService = userStatusService;
    }

    public List<UserDTO> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserConverter::userToUserDTO)
                .toList();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(UserDTO userDTO) {
        User user = UserConverter.userDTOToUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLocation(locationService.getLocationByCity(userDTO.getLocation()));
        user.setHireDate(Date.valueOf(LocalDate.now()));
        user.setUserStatus(userStatusService.getUserStatusByType(AVAILABLE));

        Role role = roleService.findByRoleName(ADMIN.name());
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    public void enableUser(String username) {
        userRepository.enableUser(username);
    }

}
