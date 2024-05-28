package com.example.demo.service;

import com.example.demo.controller.PersonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    PersonController personController;

    @Autowired
    ProgramService personService;

    private static final Logger log = LoggerFactory.getLogger(StartupHousekeeper.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.info("Start");
        if(personService.CREATE_DB_DATA_ON_STARTUP && personController.isDatabaseEmpty()) {
            personController.createNewDataIfNotCreated();
        }
    }
}