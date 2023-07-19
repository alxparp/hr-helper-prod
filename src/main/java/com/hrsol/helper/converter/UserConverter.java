package com.hrsol.helper.converter;

import com.hrsol.helper.entity.Location;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.entity.UserStatus;
import com.hrsol.helper.model.UserDTO;

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
        user.setHireDate(userDTO.getHireDate());
        user.setBirthDate(userDTO.getBirthDate());
        user.setEmail(userDTO.getEmail());
        user.setUserStatus(new UserStatus());
        user.setLocation(new Location());
        return user;
    }

}
