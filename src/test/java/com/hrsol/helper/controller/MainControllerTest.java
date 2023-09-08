package com.hrsol.helper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.model.CitiesResponseBody;
import com.hrsol.helper.model.LetterResponseBody;
import com.hrsol.helper.model.LetterTypeCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLettersByType() throws Exception {

        String urlLetterType = "/main/letterType";
        LetterTypeCriteria letterTypeCriteria = DummyObjects.getClickCriteria();

        ResultActions result = performPostRequest(generateJSON(letterTypeCriteria), urlLetterType);
        result.andExpect(status().is2xxSuccessful());

        letterTypeCriteria.setId(null);
        LetterResponseBody letterResponseBody = generateRequest(generateJSON(letterTypeCriteria), urlLetterType);
        Assertions.assertEquals("Id can't be null!", letterResponseBody.getMsg());

        letterTypeCriteria.setId(-1L);
        letterResponseBody = generateRequest(generateJSON(letterTypeCriteria), urlLetterType);
        Assertions.assertEquals("Value can't be less than 0", letterResponseBody.getMsg());

        letterTypeCriteria.setId(20L);
        letterResponseBody = generateRequest(generateJSON(letterTypeCriteria), urlLetterType);
        Assertions.assertEquals("Such letter type/letter doesn't exist", letterResponseBody.getMsg());
    }

    private LetterResponseBody generateRequest(String request, String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ResultActions result = performPostRequest(request, url);
        MvcResult expect = result.andExpect(status().is4xxClientError()).andReturn();
        return mapper.readValue(expect.getResponse().getContentAsString(), LetterResponseBody.class);
    }

    private String generateJSON(LetterTypeCriteria letterTypeCriteria) {
        return "{\"id\":" + letterTypeCriteria.getId() + ", " +
                "\"page\":" + letterTypeCriteria.getPage().orElseThrow() + ", " +
                "\"size\":" + letterTypeCriteria.getSize().orElseThrow() + "}";
    }

    private ResultActions performPostRequest(String request, String urlTemplate) throws Exception {
        return mockMvc.perform(post(urlTemplate)
                        .with(user("linda").password("password").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request));
    }

    @Test
    void approveLetter() throws Exception {
        String requestJson = "{\"id\": %s}";
        String urlApprove = "/main/approveLetter";

        ResultActions result = performPostRequest(String.format(requestJson,1L), urlApprove);
        result.andExpect(status().is2xxSuccessful());

        LetterResponseBody letterResponseBody = generateRequest(String.format(requestJson, "null"), urlApprove);
        Assertions.assertEquals("Id can't be null!", letterResponseBody.getMsg());

        letterResponseBody = generateRequest(String.format(requestJson, "-1"), urlApprove);
        Assertions.assertEquals("Value can't be less than 0", letterResponseBody.getMsg());

        letterResponseBody = generateRequest(String.format(requestJson, "20"), urlApprove);
        Assertions.assertEquals("Such letter type/letter doesn't exist", letterResponseBody.getMsg());
    }

    @Test
    void getLocationsDefault() throws Exception {
        String urlTemplate = "/main/locationsDefault";;

        CitiesResponseBody citiesResponseBody = generateGetRequest(urlTemplate);
        Assertions.assertEquals(List.of("Odesa", "Kiev"), citiesResponseBody.getCities());
    }

    private CitiesResponseBody generateGetRequest(String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ResultActions result = performGetRequest(url);
        MvcResult expect = result.andExpect(status().is2xxSuccessful()).andReturn();
        return mapper.readValue(expect.getResponse().getContentAsString(), CitiesResponseBody.class);
    }

    private ResultActions performGetRequest(String urlTemplate) throws Exception {
        return mockMvc.perform(get(urlTemplate)
                .with(user("linda").password("password").roles("ADMIN")));
    }

    @Test
    void getLettersByCities() throws Exception {
        String requestJson = "{\"id\": %s, \"cities\": [%s,%s]}";
        String urlTemplate = "/main/lettersByCities";

        ResultActions result = performPostRequest(String.format(requestJson,1L,"\"Odesa\"", "\"Kiev\""), urlTemplate);
        result.andExpect(status().is2xxSuccessful());
    }
}