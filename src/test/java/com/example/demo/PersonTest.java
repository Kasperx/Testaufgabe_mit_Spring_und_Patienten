package com.example.demo;

import com.example.demo.controller.PersonController;
import com.example.demo.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PersonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonController personController;

    static final String url = "http://localhost:8000";

    static final String urlCreateData = "http://localhost:8000/createMoreData?username=admin&pw=secret";

    private static final Logger log = LoggerFactory.getLogger(PersonTest.class);

    /*
    @DisplayName("Start -> Request")
    @BeforeTestClass
    public void start(){
        log.info("Start -> Request");
    }
    @Test
    @DisplayName("Start -> Request")
    void EnsureWebsiteReturnsStatusOk() throws IOException, InterruptedException {
        log.info("Start -> Request");
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        org.junit.jupiter.api.Assertions.assertEquals(response.toString(), "200");
    }
     */

    @Test
    @ResponseBody
    @DisplayName("1st lvl: basic request, get status http.ok")
    public void testRequest() throws Exception {
        log.info("1st lvl: basic request, get status http.ok");
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                ;
        createWebsiteData();
    }
    /*
    @Test
    @DisplayName("1st lvl: basic request, get status http.ok, get json response with data path.")
    public void testRequestWithJson() throws Exception {
        log.info("1st lvl: basic request, get status http.ok, get json response with data path.");
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$."+personService.NAME_FOR_MODEL_DATA).exists())
                ;
    }
     */

    private void createWebsiteData() throws Exception {
        log.info("Create data with db:");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(urlCreateData)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @ResponseBody
    @DisplayName("Next lvl: basic request, get status http.ok, get json response with data path.")
    public void testRequestWithJson() throws Exception {
        log.info("Next lvl: basic request, get status http.ok, get json response with data path.");
        log.info("Result:");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$."+personService.NAME_FOR_MODEL_DATA).exists())
                .andReturn();
        //mvcResult
                //.andReturn().getResponse().getContentAsString()
//                .andExpect(MockMvcResultMatchers.jsonPath("$."+personService.NAME_FOR_MODEL_DATA).exists());
        /*
        mockMvc.perform(MockMvcRequestBuilders
                //.get(url)
                .post(urlCreateData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andReturn().getResponse().getContentAsString()
                .andExpect(MockMvcResultMatchers.jsonPath("$."+personService.NAME_FOR_MODEL_DATA).exists())
        ;
         */
    }

    @AfterTestClass
    @DisplayName("End -> Clean")
    public void clean() {
        log.info("End -> Clean");
    }
    @Nested
    class Start {

        /*
        @DisplayName("Next lvl -> tell program to create data")
        public void createData() {
            log.info("Next lvl -> tell program to create data");
            try {
                resultActions = mockMvc
                        .perform(MockMvcRequestBuilders
                                .get("?username=admin&pw=secret")
                                .accept(MediaType.APPLICATION_JSON)
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        */

        //@Mock
        //PersonController personController = Mockito.mock(PersonController.class);

        @Test
        @DisplayName("Next lvl -> tell program to create data")
        public void testCallMethod(){
            log.info("Next lvl -> tell program to create data");
        }

        @Test
        @DisplayName("PersonTest Json:model exists")
        public void testJsonPersonsExist() {
            log.info("PersonTest Json:model exists");
        }

        @Nested
        class IfExists {

            @Test
            @DisplayName("PersonTest Json:model is an array & is not empty")
            public void TestJsonPersonsListIsNotEmpty() {
                log.info("PersonTest Json:model is an array & is not empty");
            }

            @Test
            @DisplayName("PersonTest Json:abc does not exist")
            public void TestJsonWrongPersonsDoNotExist() {
                log.info("PersonTest Json:abc does not exist");
            }
            @Test
            @DisplayName("Test1 Json:abc does not exist")
            public void TestJsonWrongPersonsDoNotExist1() {
                log.info("PersonTest Json:abc does not exist");
            }

            @Test
            @DisplayName("PersonTest Json:model[].id exists")
            public void TestJsonPersonsIdExist() {
                log.info("PersonTest Json:model[].id exists");
            }
        }
        @AfterTestClass
        public void clean(){
            /*if(personController != null){
                personController = null;
            }
             */
        }
    }
}
