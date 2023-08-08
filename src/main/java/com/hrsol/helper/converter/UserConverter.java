package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Location;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.entity.UserStatus;
import com.hrsol.helper.model.dto.UserDTO;

import java.sql.Date;

public class UserConverter {

    public static UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setHireDate(user.getHireDate());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserStatus(user.getUserStatus().getType());
        userDTO.setLocation(user.getLocation().getCity());
        return userDTO;
    }

    public static User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getHireDate() != null)
            user.setHireDate(new Date(userDTO.getHireDate().getTime()));
        if (userDTO.getBirthDate() != null)
            user.setBirthDate(new Date(userDTO.getBirthDate().getTime()));
        user.setEmail(userDTO.getEmail());
        user.setUserStatus(new UserStatus());
        user.setLocation(new Location());
        return user;
    }

}
