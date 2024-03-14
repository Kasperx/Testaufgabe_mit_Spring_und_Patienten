package com.example.demo.controller;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.service.PersonService.*;

@RestController
@RequestMapping("/persons")
//@RequestMapping("")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    private final PersonRepository personRepository;

    @Autowired
    Environment environment;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/createMoreData")
    public void createMoreData(@RequestParam String username, @RequestParam String pw) throws InvalidParameterException {
        if(isAdminAccount(username, pw)) {
            List<Person> personList = createNewData();
            log.info("Saving all data ("+personList.size()+") to database.");
            personRepository.saveAll(personList);
        }
    }

    @PostMapping("/createData")
    public void createDBTable (@RequestParam(required = false) String username, @RequestParam(required = false) String pw) throws InvalidParameterException {
        try {
            if (isAdminAccount(username, pw)) {
                personRepository.saveAll(createNewData());
            } else {
                return;
            }
        } catch (InvalidParameterException ipe) {
            log.error(ipe.getMessage());
            return;
        }
    }

    @GetMapping("")
    public Iterable<Person> findAllPersons(@RequestParam(required = false) String username, @RequestParam(required = false) String pw) throws InvalidParameterException {
        try{
            if(isAdminAccount(username, pw)){
                return getData(true);
            } else {
                return getData(false);
            }
        } catch (InvalidParameterException ipe) {
            log.error(ipe.getMessage());
            return getData(false);
        }
    }

    //@GetMapping("/admin/{username}{pw}")
    @GetMapping("/admin")
    public Iterable<Person> findAllAdmins(@RequestParam(required = false) String username, @RequestParam(required = false) String pw) throws InvalidParameterException {
        try{
            if(isAdminAccount(username, pw)){
                return getDataForAdmin();
            } else {
                return null;
            }
        } catch (InvalidParameterException ipe) {
            log.error(ipe.getMessage());
            return null;
        }
    }

    Iterable<Person> getData (boolean isAdmin) {
        List<Map<String, Object>> personList = jdbcTemplate.queryForList("select * from " + PersonService.databaseName + " limit 1");
        if (personList.isEmpty()) {
            personRepository.saveAll(createNewData());
        }
        if (isAdmin) {
            if(careAboutPersonalData) {
                return convertObjectToList(personRepository.findAll());
            } else {
                return personRepository.findAll();
            }
        } else {
            return getPersonsWithSomeData(personRepository.findAll());
        }
    }
    List<Person> getPersonsWithSomeData(Iterable<Person> personIterable){
        List<Person> personList = new ArrayList<>();
        Person person;
        for(Person temp: personIterable){
            person = new Person();
            person.setFirstName(temp.getFirstName());
            person.setLastName(temp.getLastName());
            person.setAge(temp.getAge());
            personList.add(person);
        }
        return personList;
    }
    Iterable<Person> getDataForAdmin () {
        List<Map<String, Object>> personList = jdbcTemplate.queryForList("select * from " + databaseName + " limit 1;");
        if (personList.isEmpty()) {
            personRepository.saveAll(createNewData());
        }
        return convertObjectToList(jdbcTemplate.queryForList("select * from " + databaseName + " where isadmin is not true;"));
    }
}
