package com.hrsol.helper.service;

import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;

    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public void register(UserDTO userDTO) {
        User user = userService.saveUser(userDTO);
//        ConfirmationToken token =
//                confirmationTokenService.saveConfirmationToken(createToken(userDTO.getUsername()));
//
//        String link = "http://localhost:8080/confirm?token=" + token.getToken();
//        emailService.send(
//                user.getEmail(),
//                buildEmail(user.getFirstName(), link));
//        LOGGER.info(String.format("User %s has registered", user.getUsername()));
    }

}
