package com.example.demo;

import com.example.demo.controller.PersonController;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonController personController;

    @InjectMocks
    PersonService personService;

    static final String url = "http://localhost:8000";

    //static final String urlCreateData = "http://localhost:8000/createMoreData?username=admin&pw=secret";
    static final String urlCreateData = "http://localhost:8000?username=admin&pw=secret";

    private static final Logger log = LoggerFactory.getLogger(PersonTest.class);

    private List<Person> personList;

    @DisplayName("Start -> Request")
    @BeforeTestClass
    public void start() throws Exception {
        log.info("Start -> Request");
        mockMvc.perform(MockMvcRequestBuilders
                        .get(urlCreateData)
                        .accept(MediaType.APPLICATION_JSON));
        //personService.setCREATE_DATA_FOR_TEST(true);
        personList.addAll(PersonService.createNewData(10));
        //person = new Person();
    }

    @Test
    @DisplayName("Test. Get new person list.size")
    void testPersonObjectList(){
        //when(PersonService.createNewData(10)).
        assertEquals(
        "Test: Create new list with objects with size of 10",
        10,
                PersonService.createNewData(10).size());
    }

    @Test
    @DisplayName("Test. Get new person != null")
    void testPersonObjectNotNull(){
        Assert.notNull(
                PersonService.createNewData().get(0),
                "Test: Get new person != null");
    }


    /*
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
    public void testPersonListCountObjects(){
         List<Person> personList = new ArrayList<>(PersonService.createNewData(10));
         assertEquals("Created new list with 10 objects", 10, personList.size());
         personList.addAll(PersonService.createNewData(10));
         assertEquals("Expanded list with 10 more objects", 20, personList.size());
    }

    @Test
    public void testStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    public void testStatusOkAndJsonPathExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$."+personService.NAME_FOR_MODEL_DATA).exists())
                ;
    }

    @AfterTestClass
    @DisplayName("End -> Clean")
    public void clean() {
        log.info("End -> Clean");
        personService.setCREATE_DATA_FOR_TEST(false);
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
