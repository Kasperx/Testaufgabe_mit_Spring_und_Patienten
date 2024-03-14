package com.example.demo.service;

import com.example.demo.entity.Person;
import com.github.javafaker.Faker;
import jakarta.persistence.Column;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
//@EnableWebMvc
public class PersonService implements WebMvcConfigurer {

    public static final String MESSAGE_ERROR_WRONG_PARAMETERS = "ERROR: Wrong parameters for admin account.";
    public static final String MESSAGE_ERROR_EMPTY_PARAMETERS = "ERROR: Empty parameters for admin account.";
    public static final String TEXT_EMAIL_ADDRESS_FOR_PERSON = "@gmail.com";
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    public static final String databaseName = "PERSON";
    public static final boolean careAboutPersonalData = true;


    public static boolean isAdminAccount (String username, String pw) throws InvalidParameterException {
        if(isParameterEmpty(username, pw)) {
            throw new InvalidParameterException(MESSAGE_ERROR_EMPTY_PARAMETERS);
        } else if (username.equals("admin") && pw.equals("secret")) {
            return true;
        } else {
            throw new InvalidParameterException(MESSAGE_ERROR_WRONG_PARAMETERS);
        }
    }

    static boolean isParameterEmpty (String username, String pw) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(pw);
    }

    public static List<Person> convertObjectToList (List<Map<String, Object>> mapList){
        List<Person> personListTemp = new ArrayList<>();
        for(Map obj: mapList){
            Person person = new Person();
            for(Object temp: obj.entrySet()) {
                if(temp.toString().split("=")[0].equalsIgnoreCase("FIRSTNAME")) {
                    person.setFirstName(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("LASTNAME")) {
                    person.setLastName(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("EMAIL")) {
                    person.setEmail(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("AGE")) {
                    person.setAge(
                            Integer.parseInt(temp.toString().split("=")[1]));
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("PASSWORD")) {
                    if(careAboutPersonalData){
                        person.setPassword("***");
                    } else {
                        person.setPassword(
                                temp.toString().split("=")[1]);

                    }
                }
            }
            personListTemp.add(person);
            log.info(obj.toString());
        }
        return personListTemp;
    }



    public static List<Person> convertObjectToList (Iterable<Person> personIterable){
        List<Person> personListTemp = new ArrayList<>();
        for(Person temp: personIterable){
            Person person = new Person();
            person.setFirstName(
                    temp.getFirstName());
            person.setLastName(
                    temp.getLastName());
            person.setEmail(
                    temp.getEmail());
            person.setAge(
                    temp.getAge());
            if(careAboutPersonalData){
                    person.setPassword("***");
            } else {
                    person.setPassword(
                            temp.getPassword());
            }
            log.info(person.toString());
            personListTemp.add(person);
        }
        return personListTemp;
    }

    List<String> getTableColumnNames () {
        List<String> columns = new ArrayList<String>();
        for (Field field : Person.class.getDeclaredFields()) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                columns.add(col.name());
                System.out.println("Columns: "+col);
            }
        }
        return columns;
    }
    public static String getTabsForConsoleOut(String text){
        final int countCharsFor1Tab = 6;
        final int countCharsFor2Tabs = 10;
        if(StringUtils.isBlank(text)){
            return "";
        } else {
            if (text.length() < countCharsFor1Tab) {
                return "\t\t\t";
            } else if (text.length() < countCharsFor2Tabs) {
                return "\t\t";
            } else {
                return "\t";
            }
        }
    }
    private static final String pathToWebFiles = "/resources/webapp";
    public static List<Person> createNewDataWithAdmin() {
        List<Person> personList = new ArrayList<>();
        // Create admin person
        log.info("Creating data for admin.");
        personList.add(getNewPerson(true));
        // Create random person
        personList.addAll(createNewData());
        log.info("Saving all "+personList.size()+" data to database.");
        return personList;
    }

    public static List<Person> createNewData() {
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

    public static List<Person> convertObjectToPerson(List<Object> objects){
        if(objects == null){
            return null;
        } else {
            List<Person> temp = new ArrayList<>();

            for(Object obj: objects){
                log.info(obj.toString());
            }
            return temp;
        }
    }

    public static List<Person> filterPersonData(List<Person> personList) {
        return filterPersonData(false, personList);
    }
    public static List<Person> filterPersonData(boolean isAdmin, List<Person> personList){
        List<Person> temp = new ArrayList<>();
        if(isAdmin){
            for (Person person : personList) {
                temp.add(new Person(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        (careAboutPersonalData
                                ? "***"
                                : person.getPassword()),
                        person.getAge(),
                        person.isAdmin()
                ));
            }
        } else {
            for (Person person : personList) {
                if (!person.isAdmin()) {
                    temp.add(new Person(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAge()
                    ));
                }
            }
        }
        return temp;
    }
}
