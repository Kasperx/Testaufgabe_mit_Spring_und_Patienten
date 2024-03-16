package com.example.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TestPersonService {

    private static final Logger log = LoggerFactory.getLogger(TestPersonService.class);

    @Autowired
    MockMvc mockMvc;

    ResultActions resultActions;

    @BeforeTestClass
    public void start(){
        try {
            resultActions = mockMvc
                    .perform(MockMvcRequestBuilders
                    .get("")
                    .accept(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTestClass
    public void clean(){
        if(resultActions != null){
            resultActions = null;
        }
        if(mockMvc != null){
            mockMvc = null;
        }
    }

    @Test
    public void getData(){
    }

    @Test
    public void TestJsonPersonsExist(){
        log.info("Test: Test Json:Person exists");
        if (resultActions != null) {
            try {
                resultActions.andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.persons").exists());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
    @Test
    public void TestJsonPersonsListIsNotEmpty(){
        log.info("Test: Test Json:Person is an array & is not empty & ");
        if (resultActions != null) {
            try {
                resultActions.andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.persons").isArray())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.persons").isNotEmpty());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
    @Test
    public void TestJsonWrongPersonsDoNotExist(){
        log.info("Test: Test Json:abc does not exist");
        if (resultActions != null) {
            try {
                resultActions.andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.abc").doesNotExist());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
    @Test
    public void TestJsonPersonsIdExist(){
        log.info("Test: Test Json:Person.id exists");
        if (resultActions != null) {
            try {
                resultActions.andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.persons[*].id").exists());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
