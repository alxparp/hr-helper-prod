package com.hrsol.helper.controller;

import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.UserConverter;
import com.hrsol.helper.entity.User;
import com.hrsol.helper.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class RegistrationControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private MockMvc mockMvc;
    private User user;

    @BeforeEach
    void setUp() {
        user = DummyObjects.getUser();
    }

    @Test
    void registration() throws Exception {
        UserDTO userDTO = UserConverter.userToUserDTO(user);
        ResultActions resultActions = create(userDTO);
        resultActions.andExpect(status().is3xxRedirection());

        userDTO.setUsername(null);
        resultActions = create(userDTO);
        resultActions.andExpect(status().is2xxSuccessful());

        userDTO.setUsername(user.getUsername());
        resultActions = create(userDTO);
        resultActions.andExpect(status().is2xxSuccessful());
    }

    ResultActions create(UserDTO userDTO) throws Exception {
        return mockMvc.perform(post("http://localhost:" + port + "/register/save")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("username", userDTO.getUsername())
                .param("password", userDTO.getPassword())
                .param("email", userDTO.getEmail())
                .param("firstName", userDTO.getFirstName())
                .param("lastName", userDTO.getLastName())
                .param("hireDate", userDTO.getHireDate().toString())
                .param("birthDate", userDTO.getBirthDate().toString())
                .param("location", userDTO.getLocation()));
    }
}