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
    public void createMoreData(@RequestParam String username, @RequestParam String pw) throws InvalidParameterException {
        if(isAdminAccount(username, pw)) {
            List<Person> personList = createNewData();
            log.info("Saving all data ("+personList.size()+") to database.");
            personRepository.saveAll(personList);
        }
    }

    @GetMapping("")
    public String loadData(@RequestParam(required = false) String username, @RequestParam(required = false) String pw, Model model){
    //public String loadData(Model model){
        //return "forward:/resources/templates/index.html";
        createNewDataIfNotCreated();
        try {
            List<Person> personList = filterPersonData(isAdminAccount(username, pw), personRepository.findAll());
            //List<Person> personList = PersonService.getData(personRepository.findAll());
            model.addAttribute("persons", personList);
        } catch (InvalidParameterException ipe){
            List<Person> personList = PersonService.filterPersonData(personRepository.findAll());
            model.addAttribute("persons", personList);
            model.addAttribute("message", ipe.getMessage());
            log.error(ipe.getMessage());
        } catch (Exception e){
            model.addAttribute("message", e.getMessage());
            log.error("", e);
        }
        return htmlFile;
    }
    void createNewDataIfNotCreated(){
        //if(personRepository.findAll().isEmpty()){
        if(jdbcTemplate.queryForList("select * from " + databaseName + " limit 1;").isEmpty()){
            List<Person> personList = createNewDataWithAdmin();
            log.info("Saving all data ("+personList.size()+") to database.");
            personRepository.saveAll(personList);
        }
    }
}
