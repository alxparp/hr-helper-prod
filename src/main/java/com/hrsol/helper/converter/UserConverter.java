package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Location;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.entity.UserStatus;
import com.hrsol.helper.model.UserDTO;

public class UserConverter {

    public static User userDTOToUser(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .hireDate(userDTO.getHireDate())
                .birthDate(userDTO.getBirthDate())
                .email(userDTO.getEmail())
                .userStatus(new UserStatus())
                .location(new Location())
                .build();
    }

    public static UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hireDate(user.getHireDate())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .userStatus(user.getUserStatus().getType())
                .location(user.getLocation().getCity())
                .build();
    }

}
