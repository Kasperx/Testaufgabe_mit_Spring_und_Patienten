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
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.service.ProgramService.*;

@RestController
@RequestMapping("")
@EnableWebMvc
public class ProgramController {

    private static final Logger log = LoggerFactory.getLogger(ProgramController.class);

    @Autowired
    Environment environment;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    VerordnungRepository verordnungRepository;

    @Autowired
    ProgramService programService;

    final String htmlFile = "index.html";

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

    @GetMapping("/kundennummer")
    @ResponseBody
    public List<Verordnung> loadData(@RequestParam String id){
        List<Verordnung> verordnungList = verordnungRepository.findAll();
        List<Verordnung> results = null;
        if(!verordnungList.isEmpty()){
            // find patient.id.versichertennummer -> compare with parameter
            for(Verordnung verordnung: verordnungList){
                if(id.equals(verordnung.getPatient_id().getVersichertennummer())){
                    if(results == null){
                        results = new ArrayList<>();
                    }
                    log.info("Found: Verordnung id="+verordnung.getId()+" with Patient.id = "+id);
                    results.add(verordnung);
                }
            }
        }
        return results;
    }

    @PostMapping("/save")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<String> saveData(
            @RequestParam String ausstellungsdatum,
            @RequestParam String einreichungsdatum,
            @RequestParam String geburtstag,
            @RequestParam String belegnummer
    ){
        switch (programService.isDataValid(
            ausstellungsdatum,
            einreichungsdatum,
            geburtstag,
            belegnummer
        )){
            case YES -> {
                //verordnungRepository.save();
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(IsDataValid.YES.toString());
            }
            case AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM -> {
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .body(IsDataValid.AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM.toString());
            }
            case BELEG_MEHRFACH_VORHANDEN -> {
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .body(IsDataValid.BELEG_MEHRFACH_VORHANDEN.toString());
            }
            case GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM -> {
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .body(IsDataValid.GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM.toString());
            }
            default -> {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(IsDataValid.SOMETHING_ELSE.toString());
            }
        }
    }

    @PostMapping("/saveobj")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public ResponseEntity<String> saveData(
            @RequestParam Verordnung verordnung
    ){
        log.info("Data: {}", verordnung.toString());
        // Why always null?
        if(verordnung != null) {
            switch (programService.isDataValid(verordnung)) {
                case YES -> {
                    //verordnungRepository.save(verordnung);
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(IsDataValid.YES.toString());
                }
                case AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM -> {
                    return ResponseEntity
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .body(IsDataValid.AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM.toString());
                }
                case BELEG_MEHRFACH_VORHANDEN -> {
                    return ResponseEntity
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .body(IsDataValid.BELEG_MEHRFACH_VORHANDEN.toString());
                }
                case GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM -> {
                    return ResponseEntity
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .body(IsDataValid.GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM.toString());
                }
                default -> {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(IsDataValid.SOMETHING_ELSE.toString());
                }
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(IsDataValid.EMPTY_PARAMETER.toString());
        }
    }

    @GetMapping("")
    public List<Verordnung> loadData(){
        return getDataWithoutSensibleInfos(verordnungRepository.findAll());
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
}
