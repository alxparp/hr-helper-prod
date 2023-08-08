package com.hrsol.helper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsol.helper.DummyObjects;
import com.hrsol.helper.model.AjaxResponseBody;
import com.hrsol.helper.model.ClickCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getResultViaAjax() throws Exception {

        String urlLetterType = "/main/letterType";
        ClickCriteria clickCriteria = DummyObjects.getClickCriteria();

        ResultActions result = performRequest(generateJSON(clickCriteria), urlLetterType);
        result.andExpect(status().is2xxSuccessful());

        clickCriteria.setId(null);
        AjaxResponseBody ajaxResponseBody = generateRequest(generateJSON(clickCriteria), urlLetterType);
        Assertions.assertEquals("Id can't be null!", ajaxResponseBody.getMsg());

        clickCriteria.setId(-1L);
        ajaxResponseBody = generateRequest(generateJSON(clickCriteria), urlLetterType);
        Assertions.assertEquals("Value can't be less than 0", ajaxResponseBody.getMsg());

        clickCriteria.setId(20L);
        ajaxResponseBody = generateRequest(generateJSON(clickCriteria), urlLetterType);
        Assertions.assertEquals("Such letter type doesn't exist", ajaxResponseBody.getMsg());
    }

    private AjaxResponseBody generateRequest(String request, String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ResultActions result = performRequest(request, url);
        MvcResult expect = result.andExpect(status().is4xxClientError()).andReturn();
        return mapper.readValue(expect.getResponse().getContentAsString(), AjaxResponseBody.class);
    }

    private String generateJSON(ClickCriteria clickCriteria) {
        return "{\"id\":" + clickCriteria.getId() + ", " +
                "\"page\":" + clickCriteria.getPage().orElseThrow() + ", " +
                "\"size\":" + clickCriteria.getSize().orElseThrow() + "}";
    }

    private ResultActions performRequest(String request, String urlTemplate) throws Exception {
        return mockMvc.perform(post(urlTemplate)
                        .with(user("linda").password("password").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request));
    }

    @Test
    void approveLetter() throws Exception {
        String requestJson = "{\"id\": %s}";
        String urlApprove = "/main/approveLetter";

        ResultActions result = performRequest(String.format(requestJson,1L), urlApprove);
        result.andExpect(status().is2xxSuccessful());

        AjaxResponseBody ajaxResponseBody = generateRequest(String.format(requestJson, "null"), urlApprove);
        Assertions.assertEquals("Id can't be null!", ajaxResponseBody.getMsg());

        ajaxResponseBody = generateRequest(String.format(requestJson, "-1"), urlApprove);
        Assertions.assertEquals("Value can't be less than 0", ajaxResponseBody.getMsg());

        ajaxResponseBody = generateRequest(String.format(requestJson, "20"), urlApprove);
        Assertions.assertEquals("Such letter doesn't exist", ajaxResponseBody.getMsg());
    }
}