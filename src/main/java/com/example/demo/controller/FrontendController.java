package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import static com.example.demo.service.PersonService.*;

//@RestController
@Controller
//@RequestMapping("/index.html")
//@RequestMapping("/")
@RequestMapping("")
public class FrontendController {

    private static final Logger log = LoggerFactory.getLogger(FrontendController.class);

    //private final PersonRepository personRepository;

    @Autowired
    Environment environment;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PersonRepository personRepository;
    final String htmlFile = "index";

    //public FrontendController(PersonRepository frontendController) { this.personRepository = frontendController; }

    @PostMapping("/createMoreData")
    public void createMoreData(@RequestParam(required = false) String username, @RequestParam(required = false) String pw, Model model){
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
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute("message",
                        "Did not create new data: " + IsAdmin.EMPTY_PARAMETER.toString());
            }
            case WRONG_PARAMETER -> {
                log.error(IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute("message",
                        "Did not create new data: " + IsAdmin.WRONG_PARAMETER.toString());
            }
        }
    }

    @GetMapping("")
    public String loadData(@RequestParam(required = false) String username, @RequestParam(required = false) String pw, Model model){
    //public String loadData(Model model){
        //return "forward:/resources/templates/index.html";
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                //List<Person> personList = filterPersonData(true, personRepository.findAll());
                //model.addAttribute("persons", personList);
                model.addAttribute("persons", getDataWithoutSensibleInfos(true, personRepository.findAll()));
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                //List<Person> personList = filterPersonData(personRepository.findAll());
                //model.addAttribute("persons", personList);
                model.addAttribute("persons", getDataWithoutSensibleInfos(personRepository.findByIsAdminFalse()));
                model.addAttribute("message", IsAdmin.EMPTY_PARAMETER.toString());
            }
            case WRONG_PARAMETER -> {
                //log.error(IsAdmin.WRONG_PARAMETER.toString());
                //List<Person> personList = filterPersonData(personRepository.findAll());
                model.addAttribute("persons", getDataWithoutSensibleInfos(personRepository.findByIsAdminFalse()));
                model.addAttribute("message", IsAdmin.WRONG_PARAMETER.toString());
            }
        }
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
        if(withAdmin) {
            List<Person> personList = createNewDataWithAdmin();
            log.info("Saving all " + personList.size() + " data to database.");
            personRepository.saveAll(personList);
        } else {
            List<Person> personList = PersonService.createNewData();
            log.info("Saving all " + personList.size() + " data to database.");
            personRepository.saveAll(personList);
        }
    }
    private boolean isDatabaseEmpty(){
        return jdbcTemplate.queryForList("select * from " + databaseName + " limit 1;").isEmpty();
    }
}
