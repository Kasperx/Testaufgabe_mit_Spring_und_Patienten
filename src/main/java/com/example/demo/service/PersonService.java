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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    public static final boolean createDBDataOnStartup = true;

    public static enum IsAdmin {
        YES(""),
        EMPTY_PARAMETER(MESSAGE_ERROR_EMPTY_PARAMETERS),
        WRONG_PARAMETER(MESSAGE_ERROR_WRONG_PARAMETERS);
        String text;
        IsAdmin(String text){
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    public static IsAdmin isAdminAccount (String username, String pw){
        if(isParameterEmpty(username, pw)) {
            return IsAdmin.EMPTY_PARAMETER;
        } else if (username.equals("admin") && pw.equals("secret")) {
            return IsAdmin.YES;
        } else {
            return IsAdmin.WRONG_PARAMETER;
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
                    person.setFirstname(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("LASTNAME")) {
                    person.setLastname(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("EMAIL")) {
                    person.setEmail(
                            temp.toString().split("=")[1]);
                } else if(temp.toString().split("=")[0].equalsIgnoreCase("AGE")) {
                    person.setBirthdate(
                            temp.toString().split("=")[1]);
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
    /*public String getPasswordString(Person person){
        return careAboutPersonalData
                ? "***"
                : StringUtils.isBlank(person.getPassword())
                        ? ""
                        : person.getPassword();
    }
     */
    public static String getPasswordString(Person person){
        return getPasswordString(person.getPassword());
    }
    public static String getPasswordString(String password){
        return careAboutPersonalData
                ? "***"
                : StringUtils.isBlank(password)
                        ? ""
                        : password;
    }
    public static List<Person> convertObjectToList (Iterable<Person> personIterable){
        List<Person> personListTemp = new ArrayList<>();
        for(Person temp: personIterable){
            Person person = new Person();
            person.setFirstname(
                    temp.getFirstname());
            person.setLastname(
                    temp.getLastname());
            person.setEmail(
                    temp.getEmail());
            person.setBirthdate(
                    temp.getBirthdate());
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
    private static final int countCharsFor1Tab = 5;
    private static final int countCharsFor2Tabs = 10;
    public static String getTabsForConsoleOut(String text){
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
    //private static final String pathToWebFiles = "/resources/webapp";
    public static List<Person> createNewDataWithAdmin() {
        List<Person> personList = new ArrayList<>();
        // Create admin person
        log.info("Creating data for admin.");
        personList.add(getNewPerson(true));
        // Create random person
        personList.addAll(createNewData());
        log.info("Creating "+personList.size()+" data for persons.");
        return personList;
    }
    private final static int countPersonData = 10;
    public static List<Person> createNewData() {
        return createNewData(countPersonData);
    }
    private final static boolean haveMoreThan1Admin = true;
    public static List<Person> createNewData(int countPersonData) {
        List<Person> personList = new ArrayList<>();
        Person person = null;
        if(haveMoreThan1Admin) {
            // Create another admin account with random data
            person = getNewPerson(true);
            log.info("Creating data for random person. [" + person.toString() + "]");
            personList.add(person);
            countPersonData -= 1;
        }
        for (int i = 0; i < countPersonData; i++) {
            person = getNewPerson();
            log.info("Creating data for random person #"+(i+1)+". ["+person.toString()+"]");
            personList.add(person);
        }
        return personList;
    }

    static Person getNewPerson () {
        return getNewPerson(false);
    }
    Instant now = Instant.now();
    private final static LocalDate date = LocalDate.now(ZoneId.of("Europe/Berlin"));
    static Person getNewPerson (boolean isAdmin) {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        // test
        int randomDay = ThreadLocalRandom.current().nextInt(1, 30 + 1);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        int randomYear = LocalDate.now().getYear() - ThreadLocalRandom.current().nextInt(1, 100 + 1);
        String birtdata = LocalDate.of(randomYear, randomMonth, randomDay)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        // test
        return new Person(
                firstName,
                lastName,
                firstName.charAt(0) + "." + lastName + PersonService.TEXT_EMAIL_ADDRESS_FOR_PERSON,
                RandomStringUtils.random(10, true, true),
                birtdata,
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
/*
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
                        person.getBirthdata(),
                        person.isAdmin()
                ));
            }
        } else {
            for (Person person : personList) {
                if (!person.isAdmin()) {
                    temp.add(new Person(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getBirthdata()
                    ));
                }
            }
        }
        return temp;
    }
 */
    public static List<Person> getDataWithoutSensibleInfos(List<Person> personList){
        return getDataWithoutSensibleInfos(false, personList);
    }
    public static List<Person> getDataWithoutSensibleInfos(boolean isAdmin, List<Person> personList){
        if(isAdmin) {
            if (careAboutPersonalData) {
                List<Person> personList1 = new ArrayList<>();
                for (Person person : personList) {
                    person.setPassword("***");
                    personList1.add(person);
                }
                return personList1;
            } else {
                return personList;
            }
        } else {
            List<Person> personList1 = new ArrayList<>();
            for (Person person : personList) {
                person.setPassword("");
                person.setEmail("");
                person.setBirthdate("");
                personList1.add(person);
            }
            return personList1;
        }
    }
    /*public static Map<String, Boolean> showAllData (boolean isAdmin){
        return Map.of("showAllData", isAdmin);
    }*/
}
