package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@EnableTransactionManagement
public class Database {
/*
    Environment environment;
    private static final Logger log = LoggerFactory.getLogger(Database.class);

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static List<Person> getNewData () {
        List<Person> personList = new ArrayList<>();
        // Create admin person
        log.info("Creating data for admin.");
        personList.add(getNewPerson(true));
        // Create random person
        personList.addAll(getData());
        log.info("Saving all "+personList.size()+" data to database.");
        return personList;
    }

    public static List<Person> getData () {
        List<Person> personList = new ArrayList<>();
        // Create random person
        for (int i = 0; i < 10; i++) {
            Person person = getNewPerson();
            log.info("Creating data for random person #"+(i+1)+". ["+person.toString()+"]");
            //personList.add(getNewPerson(false));
            personList.add(person);
        }
        //log.info("Saving all "+personList.size()+" to database.");
        return personList;
    }

    static Person getNewPerson () {
        return getNewPerson(false);
    }
    static Person getNewPerson (boolean isAdmin) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return new Person(
                firstName,
                lastName,
                firstName.charAt(0) + "." + lastName + PersonService.TEXT_EMAIL_ADDRESS_FOR_PERSON,
                RandomStringUtils.random(10, true, true),
                ThreadLocalRandom.current().nextInt(1, 100 + 1),
                isAdmin
        );
    }

 */
}
