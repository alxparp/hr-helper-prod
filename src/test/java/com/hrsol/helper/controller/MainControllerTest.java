package com.hrsol.helper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsol.helper.model.AjaxResponseBody;
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

        Integer id = 1;
        Integer page = 1;
        Integer size = 2;

        ResultActions result = performRequest(generateJSON(id, page, size));
        result.andExpect(status().is2xxSuccessful());

        AjaxResponseBody ajaxResponseBody = generateRequest(null, page, size);
        Assertions.assertEquals("Id can't be null!", ajaxResponseBody.getMsg());

        ajaxResponseBody = generateRequest(-1, page, size);
        Assertions.assertEquals("Value can't be less than 0", ajaxResponseBody.getMsg());

        ajaxResponseBody = generateRequest(20, page, size);
        Assertions.assertEquals("Such letter type doesn't exist", ajaxResponseBody.getMsg());
    }

    private AjaxResponseBody generateRequest(Integer id, Integer page, Integer size) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String request = generateJSON(id, page, size);
        ResultActions result = performRequest(request);
        MvcResult expect = result.andExpect(status().is4xxClientError()).andReturn();
        return mapper.readValue(expect.getResponse().getContentAsString(), AjaxResponseBody.class);
    }

    private String generateJSON(Integer id, Integer page, Integer size) {
        return "{\"id\":" + id + ", \"page\":" + page + ", \"size\":" + size + "}";
    }

    private ResultActions performRequest(String request) throws Exception {
        return mockMvc.perform(post("/main/letterType")
                        .with(user("linda").password("password").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request));
    }
}