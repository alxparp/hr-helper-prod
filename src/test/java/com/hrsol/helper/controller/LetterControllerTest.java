package com.hrsol.helper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.converter.LetterConverter;
import com.hrsol.helper.model.AjaxResponseBody;
import com.hrsol.helper.model.ClickCriteria;
import com.hrsol.helper.model.dto.LetterDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class LetterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Value(value = "${local.server.port}")
    private int port;

    @Test
    void save() throws Exception {

        String urlSaveLetter = "/main/letter/create/save";
        LetterDTO letterDTO = LetterConverter.LetterToDTO(DummyObjects.getLetter());

        letterDTO.setId(null);
        letterDTO.setUsername("linda");
        ResultActions result = performRequest(getParams(letterDTO), urlSaveLetter);
        result.andExpect(status().is3xxRedirection());

        letterDTO.setName("");
        result = performRequest(getParams(letterDTO), urlSaveLetter);
        result.andExpect(status().is2xxSuccessful());

    }

    private MultiValueMap<String, String> getParams(LetterDTO letterDTO) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", letterDTO.getId() == null ? null : String.valueOf(letterDTO.getId()));
        multiValueMap.add("name", letterDTO.getName() == null ? null : letterDTO.getName());
        multiValueMap.add("dueDate", String.valueOf(letterDTO.getDueDate()));
        multiValueMap.add("letterStatus", String.valueOf(letterDTO.getLetterStatus()));
        multiValueMap.add("username", String.valueOf(letterDTO.getUsername()));
        multiValueMap.add("letterType", String.valueOf(letterDTO.getLetterType()));
        multiValueMap.add("templateType", String.valueOf(letterDTO.getTemplateType()));
        return multiValueMap;
    }

    private ResultActions performRequest(MultiValueMap<String, String> params, String urlTemplate) throws Exception {
        return mockMvc.perform(post("http://localhost:" + port + urlTemplate)
                .with(user("linda").password("password").roles("ADMIN"))
                .contentType(APPLICATION_FORM_URLENCODED)
                .params(params));
    }

    @Test
    void update() throws Exception {

        String urlUpdateLetter = "/main/letter/edit/update";
        LetterDTO letter = LetterConverter.LetterToDTO(DummyObjects.getLetter());

        letter.setId(1L);
        letter.setUsername("linda");
        letter.setName("Anniversary John Block");
        ResultActions result = performRequest(getParams(letter), urlUpdateLetter);
        result.andExpect(status().is3xxRedirection());

    }
}