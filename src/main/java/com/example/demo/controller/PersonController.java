package com.example.demo.controller;

import com.example.demo.entity.Verordnung;
import com.example.demo.repository.VerordnungRepository;
import com.example.demo.service.ProgramService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;

import static com.example.demo.service.ProgramService.*;

@RestController
//@Controller
//@RequestMapping("/index.html.backup")
//@RequestMapping("/")
@RequestMapping("")
@EnableWebMvc
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    //private final VerordnungRepository personRepository;

    @Autowired
    Environment environment;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    VerordnungRepository verordnungRepository;

    @Autowired
    ProgramService programService;

    //@Autowired
    //ViewPerson viewPerson;
    final String htmlFile = "index.html";

    //public PersonController(VerordnungRepository frontendController) { this.personRepository = frontendController; }

    @PostMapping("/createMoreData")
    @ResponseStatus(code = HttpStatus.OK)
    public ModelAndView createMoreData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            Model model,
            HttpServletResponse httpServletResponse
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(htmlFile);
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                if(isDatabaseEmpty()) {
                    programService.createNewData();
                    model.addAttribute("message",
                        "Created new data with admin account(s).");
                } else {
                    createNewData();
                    model.addAttribute("message",
                        "Created new data.");
                }
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(verordnungRepository.findAll()));
                model.addAttribute(programService.NAME_FOR_MODEL_PERMISSION, true);
            }
            case EMPTY_PARAMETER -> {
                log.error(ProgramService.IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE,
                        "Did not create new data: " + ProgramService.IsAdmin.EMPTY_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(verordnungRepository.findAll()));
                model.addAttribute(programService.NAME_FOR_MODEL_PERMISSION, false);
            }
            case WRONG_PARAMETER -> {
                log.error(ProgramService.IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE,
                        "Did not create new data: " + ProgramService.IsAdmin.WRONG_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(verordnungRepository.findAll()));
                model.addAttribute(programService.NAME_FOR_MODEL_PERMISSION, false);
            }
        }
        return modelAndView;
    }
    /*
    @PostMapping("/findData")
    @ResponseStatus(code = HttpStatus.OK)
    public void findData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            Model model,
            HttpServletResponse httpServletResponse){
        Patient person = (Patient) model.getAttribute(personService.NAME_FOR_MODEL_DATA);
        if(person != null) {
            List<Patient> personList = null;
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
     */
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
                Optional<Verordnung> optionalPerson = verordnungRepository.findById(id);
                if(optionalPerson.isPresent()) {
                    verordnungRepository.delete(optionalPerson.get());
                } else {
                    log.error("Error: Did not find data with id {}", id);
                    model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE,
                            "Did not find person with id "+id+" -> did not remove data: " + ProgramService.IsAdmin.EMPTY_PARAMETER.toString());
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            case EMPTY_PARAMETER -> {
                log.error(ProgramService.IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE,
                        "Did not remove data: " + ProgramService.IsAdmin.EMPTY_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            case WRONG_PARAMETER -> {
                log.error(ProgramService.IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE,
                        "Did not remove data: " + ProgramService.IsAdmin.WRONG_PARAMETER.toString());
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
    /*
    @GetMapping("")
    public ModelAndView loadData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw,
            Model model
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(htmlFile);
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                log.info(IsAdmin.YES.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(personRepository.findAll()));
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_PERMISSION, false);
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE, IsAdmin.EMPTY_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(personRepository.findAll()));
            }
            case WRONG_PARAMETER -> {
                log.error(IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_PERMISSION, false);
                model.addAttribute(programService.NAME_FOR_MODEL_MESSAGE, IsAdmin.WRONG_PARAMETER.toString());
                model.addAttribute(programService.NAME_FOR_MODEL_DATA, getDataWithoutSensibleInfos(personRepository.findAll()));
            }
        }
        //model.addAttribute("Patient", viewPerson);
        return modelAndView;
    }
    */
    @GetMapping("")
    public List<Verordnung> loadData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(htmlFile);
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                log.info(IsAdmin.YES.toString());
                return getDataWithoutSensibleInfos(verordnungRepository.findAll());
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                return getDataWithoutSensibleInfos(verordnungRepository.findAll());
            }
            case WRONG_PARAMETER -> {
                log.error(IsAdmin.WRONG_PARAMETER.toString());
                return getDataWithoutSensibleInfos(verordnungRepository.findAll());
            }
        }
        return null;
    }
    /*
    @GetMapping("")
    public List<Patient> loadData(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String pw
    ){
        switch (isAdminAccount(username, pw)) {
            case YES -> {
                log.info(IsAdmin.YES.toString());
                if(personService.CREATE_DB_DATA_ON_STARTUP && isDatabaseEmpty()) {
                    createNewData(true);
                }
                return getDataWithoutSensibleInfos(personRepository.findAll());
            }
            case EMPTY_PARAMETER -> {
                log.error(IsAdmin.EMPTY_PARAMETER.toString());
                return getDataWithoutSensibleInfos(personRepository.findAll());
            }
            case WRONG_PARAMETER -> {
                log.error(IsAdmin.WRONG_PARAMETER.toString());
                return getDataWithoutSensibleInfos(personRepository.findAll());
            }
        }
        return null;
    }
    */

    public void createNewDataIfNotCreated(){
        if(isDatabaseEmpty()){
            List<Verordnung> verordnungList = programService.createNewData();
            log.info("Saving all "+verordnungList.size()+" data to database.");
            verordnungRepository.saveAll(verordnungList);
        }
    }

    public boolean isDatabaseEmpty(){
        return jdbcTemplate.queryForList("select * from " + programService.DATABASE_NAME + " limit 1;").isEmpty();
    }

    /*@Service
    public static class ViewPerson{
        String name;
        String pw;
    }
     */
}
