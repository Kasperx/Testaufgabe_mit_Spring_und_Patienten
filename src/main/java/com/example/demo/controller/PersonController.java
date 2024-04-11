package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.service.PersonService.*;

//@RestController
@Controller
//@RequestMapping("/index.html")
//@RequestMapping("/")
@RequestMapping("")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    //private final PersonRepository personRepository;

    @Autowired
    Environment environment;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    //@Autowired
    //ViewPerson viewPerson;
    final String htmlFile = "index";

    //public PersonController(PersonRepository frontendController) { this.personRepository = frontendController; }

    @PostMapping("/createMoreData")
    @ResponseStatus(code = HttpStatus.OK)
    public String createMoreData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            Model model,
            HttpServletResponse httpServletResponse){
        //viewPerson = (ViewPerson) model.getAttribute("Person");
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                if(isDatabaseEmpty()) {
                    createNewData(true);
                    model.addAttribute("message",
                        "Created new data with admin account(s).");
                } else {
                    createNewData();
                    model.addAttribute("message",
                        "Created new data.");
                }
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personRepository.findAll()));
                model.addAttribute(personService.NAME_FOR_MODEL_PERMISSION, true);
            }
            case EMPTY_PARAMETER -> {
                log.error(PersonService.IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE,
                        "Did not create new data: " + PersonService.IsAdmin.EMPTY_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(false, personRepository.findAll()));
                model.addAttribute(personService.NAME_FOR_MODEL_PERMISSION, false);
            }
            case WRONG_PARAMETER -> {
                log.error(PersonService.IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE,
                        "Did not create new data: " + PersonService.IsAdmin.WRONG_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(false, personRepository.findAll()));
                model.addAttribute(personService.NAME_FOR_MODEL_PERMISSION, false);
            }
        }
        //model.addAttribute("Person", viewPerson);
        return htmlFile;
    }
    @PostMapping("/findData")
    @ResponseStatus(code = HttpStatus.OK)
    public void findData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            Model model,
            HttpServletResponse httpServletResponse){
        Person person = (Person) model.getAttribute(personService.NAME_FOR_MODEL_DATA);
        if(person != null) {
            List<Person> personList = null;
            if (!(personList = personRepository.findByEmail(person.getEmail())).isEmpty()) {
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personList));
            } else if (!(personList = personRepository.findByEmailStartsWith(person.getEmail())).isEmpty()) {
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personList));
            } else if (!(personList = personRepository.findByFirstname(person.getFirstname())).isEmpty()) {
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personList));
            } else if (!(personList = personRepository.findByLastname(person.getLastname())).isEmpty()) {
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personList));
            }
        }
    }
    @PostMapping("/removeData/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            @PathVariable int id,
            Model model,
            HttpServletResponse httpServletResponse){
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                Optional<Person> optionalPerson = personRepository.findById(id);
                if(optionalPerson.isPresent()) {
                    personRepository.delete(optionalPerson.get());
                } else {
                    log.error("Error: Did not find data with id {}", id);
                    model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE,
                            "Did not find person with id "+id+" -> did not remove data: " + PersonService.IsAdmin.EMPTY_PARAMETER.toString());
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            case EMPTY_PARAMETER -> {
                log.error(PersonService.IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE,
                        "Did not remove data: " + PersonService.IsAdmin.EMPTY_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            case WRONG_PARAMETER -> {
                log.error(PersonService.IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE,
                        "Did not remove data: " + PersonService.IsAdmin.WRONG_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @GetMapping("")
    public String loadData(@RequestParam(required = false) String username, @RequestParam(required = false) String pw, Model model){
    //public String loadData(Model model){
        //return "forward:/resources/templates/index.html";
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                log.info(IsAdmin.YES.toString());
                if(personService.CREATE_DB_DATA_ON_STARTUP && isDatabaseEmpty()) {
                    createNewData(true);
                    model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE, "Created new data with admin account(s).");
                }
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(true, personRepository.findAll()));
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_PERMISSION, false);
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE, IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(false, personRepository.findByIsAdminFalse()));
            }
            case WRONG_PARAMETER -> {
                log.error(IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_PERMISSION, false);
                model.addAttribute(personService.NAME_FOR_MODEL_MESSAGE, IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(personService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(false, personRepository.findByIsAdminFalse()));
            }
        }
        //model.addAttribute("Person", viewPerson);
        return htmlFile;
    }
    private void createNewDataIfNotCreated(){
        if(isDatabaseEmpty()){
            List<Person> personList = createNewDataWithAdmin();
            log.info("Saving all "+personList.size()+" data to database.");
            personRepository.saveAll(personList);
        }
    }
    private void createNewData(){
        createNewData(false);
    }
    private void createNewData(boolean withAdmin){
        List<Person> personList = null;
        if(withAdmin) {
            personList = createNewDataWithAdmin();
        } else {
            personList = PersonService.createNewData();
        }
        log.info("Saving all " + personList.size() + " data to database.");
        personRepository.saveAll(personList);
    }
    private boolean isDatabaseEmpty(){
        return jdbcTemplate.queryForList("select * from " + personService.DATABASE_NAME + " limit 1;").isEmpty();
    }

    /*@Service
    public static class ViewPerson{
        String name;
        String pw;
    }
     */
}
