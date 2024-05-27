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

    private static final Logger log = LoggerFactory.getLogger(StartupHousekeeper.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        // do whatever you need here
        log.info("Start");
        personController.createNewDataIfNotCreated();
    }
}