package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Position;
import com.example.demo.entity.Verordnung;
import com.github.javafaker.Faker;
import jakarta.persistence.Column;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//@Configuration
//@EnableWebMvc
@Service
@Component
public class ProgramService implements WebMvcConfigurer {

    public static final String MESSAGE_ERROR_WRONG_PARAMETERS = "ERROR: Wrong parameters for admin account.";
    public static final String MESSAGE_ERROR_EMPTY_PARAMETERS = "ERROR: Empty parameters for admin account.";
    private static final String TEXT_EMAIL_ADDRESS_FOR_PERSON = "@gmail.com";
    private static final Logger log = LoggerFactory.getLogger(ProgramService.class);
    public final String DATABASE_NAME = "PERSON";
    public final String NAME_FOR_MODEL_DATA = "persons";
    public final String NAME_FOR_MODEL_MESSAGE = "message";
    public final String NAME_FOR_MODEL_PERMISSION = "showAllData";
    private static final boolean CARE_ABOUT_PERSONAL_DATA = true;
    public final boolean CREATE_DB_DATA_ON_STARTUP = true;
    private static int COUNT_DATA = 10;
    private final static boolean HAVE_MORE_THAN_1_ADMIN = true;
    @Setter
    private static boolean CREATE_DATA_FOR_TEST = false;
    /*
    private final static String DATA_FOR_TEST_FIRSTNAME = "Julius";
    private final static String DATA_FOR_TEST_LASTNAME = "Medikus";
    private final static int DATA_FOR_TEST_BIRTHDAY_RANDOMDAY = 6;
    private final static int DATA_FOR_TEST_BIRTHDAY_RANDOMMONTH = 6;
    private final static int DATA_FOR_TEST_BIRTHDAY_RANDOMYEAR = 2000;
    /*
    private final static String DATA_FOR_TEST_ADMIN_FIRSTNAME = "Amanda";
    private final static String DATA_FOR_TEST_ADMIN_LASTNAME = "Kambus";
    private final static int DATA_FOR_TEST_ADMIN_BIRTHDAY_RANDOMDAY = 20;
    private final static int DATA_FOR_TEST_ADMIN_BIRTHDAY_RANDOMMONTH = 12;
    private final static int DATA_FOR_TEST_ADMIN_BIRTHDAY_RANDOMYEAR = 1950;
     */

    /*
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
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
    /*
    public static String getPasswordString(Patient person){
        return getPasswordString(person.getPassword());
    }
    public static String getPasswordString(String password){
        return CARE_ABOUT_PERSONAL_DATA
                ? "***"
                : StringUtils.isBlank(password)
                        ? ""
                        : password;
    }
     */
    List<String> getTableColumnNames () {
        List<String> columns = new ArrayList<String>();
        for (Field field : Patient.class.getDeclaredFields()) {
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
    /*
    public static List<Verordnung> createNewDataWithAdmin() {
        List<Verordnung> elementList = new ArrayList<>();
        /*
        if(CREATE_DATA_FOR_TEST) {
            // Create person with specific data for test (test with random data is difficult)
            personList.add(getNewPersonForTest(false));
            // Create person with specific data for test (test with random data is difficult)
            personList.add(getNewPersonForTest(true));
            COUNT_PERSON_DATA = COUNT_PERSON_DATA - 2;
        }
         *
        // Create admin person
        log.info("Creating data for admin.");
        elementList.add(createNewPerson(true));
        // Create random person
        elementList.addAll(createNewData());
        log.info("Creating "+elementList.size()+" data for persons.");
        return elementList;
    }
    */
    public static List<Verordnung> createNewData() {
        return createNewData(COUNT_DATA);
    }
    /*
    public static List<Verordnung> createNewData(int countPersonData) {
        List<Verordnung> elementList = new ArrayList<>();
        Patient person = null;
        if(HAVE_MORE_THAN_1_ADMIN && ! CREATE_DATA_FOR_TEST) {
            // Create another admin account with random data
            person = getNewPerson(true);
            log.info("Creating data for random person. [" + person.toString() + "]");
            elementList.add(person);
            countPersonData -= 1;
        }
        for (int i = 0; i < countPersonData; i++) {
            person = getNewPerson();
            log.info("Creating data for random person #"+(i+1)+". ["+person.toString()+"]");
            elementList.add(person);
        }
        return elementList;
    }
    */
    static List<Verordnung> createNewData(int count){
        List<Verordnung> verordnungList = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Patient patient = createNewPatient();
            Position position = createNewPosition();
            Verordnung verordnung = createNewVerordnung(patient, position);
            log.info(verordnung.toString());
            verordnungList.add(verordnung);
        }
        log.info("Created "+verordnungList.size()+" data.");
        return verordnungList;
    }

    static Verordnung createNewVerordnung(Patient patient, Position position){
        Verordnung verordnung = new Verordnung();
        verordnung.setBelegnummer(String.valueOf(getRandomNumberWithLength(2)));
        verordnung.setAusstellungsdatum(
                Date.from(Instant.now())
        );
        verordnung.setKostentraeger(
                new Faker().address().cityName()
        );
        verordnung.setBetriebsstaettennummer(
                String.valueOf(getRandomNumberWithLength(9))
        );
        verordnung.setVertragsarztnummer(
                String.valueOf(getRandomNumberWithLength(9))
        );
        verordnung.setPatient_id(patient);
        verordnung.setPosition_id(position);
        return verordnung;
    }


    Instant now = Instant.now();

    private final static LocalDate date = LocalDate.now(ZoneId.of("Europe/Berlin"));

    static Position createNewPosition(){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        Position position = new Position();
        position.setPositionsnummer(
                String.valueOf(getRandomNumberWithLength(20))
        );
        String text = RandomText.getText();
        position.setPositionstext(
                text.length() <= 100
                        ? text
                        : text.substring(0, 99)
        );
        position.setEinzelpreis(
                String.format("%.02f",
                        getRandomFloatWithLength(9-2))
        );
        position.setMenge(
                String.format("%.02f",
                        getRandomFloatWithLength(6-2))
        );
        position.setMehrwertsteuersatz(
                String.format("%.02f",
                        getRandomFloatWithLength(4-2))
        );
        return position;
    }

    static Patient createNewPatient() {
        /*
        // test
        if(isAdmin){

        }
        */
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String street = faker.address().streetName();
        //String plz = String.valueOf(10000  + new Random().nextInt(90000));
        String plz = String.valueOf(getRandomNumberWithLength(5));
        String city = faker.address().cityName();
        int randomDay = ThreadLocalRandom.current().nextInt(1, 30 + 1);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        int randomYear = LocalDate.now().getYear() - ThreadLocalRandom.current().nextInt(1, 100 + 1);
        String birthdate = LocalDate.of(randomYear, randomMonth, randomDay)
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        String randomNumber = String.valueOf(100000000  + new Random().nextInt(900000000));
        return new Patient(
                firstname,
                lastname,
                birthdate,
                randomNumber,
                street,
                plz,
                city
        );
    }

    public static List<Patient> convertObjectToPerson(List<Object> objects){
        if(objects == null){
            return null;
        } else {
            List<Patient> temp = new ArrayList<>();

            for(Object obj: objects){
                log.info(obj.toString());
            }
            return temp;
        }
    }
/*
    public static List<Patient> filterPersonData(List<Patient> personList) {
        return filterPersonData(false, personList);
    }
    public static List<Patient> filterPersonData(boolean isAdmin, List<Patient> personList){
        List<Patient> temp = new ArrayList<>();
        if(isAdmin){
            for (Patient person : personList) {
                temp.add(new Patient(
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
            for (Patient person : personList) {
                if (!person.isAdmin()) {
                    temp.add(new Patient(
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
    public static List<Verordnung> getDataWithoutSensibleInfos(List<Verordnung> verordnungList){
        return getDataWithoutSensibleInfos(false, verordnungList);
    }
    public static List<Verordnung> getDataWithoutSensibleInfos(boolean isAdmin, List<Verordnung> verordnungList){
        /*
        if(isAdmin) {
            if (CARE_ABOUT_PERSONAL_DATA) {
                List<Patient> personList1 = new ArrayList<>();
                for (Patient person : personList) {
                    person.setPassword("***");
                    personList1.add(person);
                }
                return personList1;
            } else {
                return personList;
            }
        } else {
            List<Patient> personList1 = new ArrayList<>();
            for (Patient person : personList) {
                person.setPassword("");
                person.setEmail("");
                person.setBirthdate("");
                personList1.add(person);
            }
            return personList1;
        }
        */
        return verordnungList;
    }
    /*
    public static Map<String, Boolean> showAllData (boolean isAdmin){
        return Map.of("showAllData", isAdmin);
    }
    */

    static int getRandomNumberWithLength(int length){
        return new Random().nextInt(
                ((int)(Math.pow(10, length))-1
                - (int)(Math.pow(10, length-1)))
                + (int)(Math.pow(10, length-1))
        );
    }
    static float getRandomFloatWithLength(int length){
        // float random = min + r.nextFloat() * (max - min);
        return (int)(Math.pow(10, length-1)) + new Random().nextFloat() * ((int)(Math.pow(10, length)-1) - (int)(Math.pow(10, length-1)));
    }
    /*
    static int getRandomNumberWithLength(int length){
        int number = 0;
        switch (length){
            case 1:
                number = 1 + new Random().nextInt(9);
                break;
            case 2:
                number = 10 + new Random().nextInt(99);
                break;
            case 3:
                number = 100 + new Random().nextInt(999);
                break;
            case 4:
                number = 1000 + new Random().nextInt(9999);
                break;
            case 5:
                number = 10000 + new Random().nextInt(99999);
                break;
            case 6:
                number = 100000 + new Random().nextInt(999999);
                break;
            case 7:
                number = 1000000 + new Random().nextInt(9999999);
                break;
            case 8:
                number = 10000000 + new Random().nextInt(99999999);
                break;
            case 9:
                number = 100000000 + new Random().nextInt(999999999);
                break;
        }
        return number;
    }
     */
}
