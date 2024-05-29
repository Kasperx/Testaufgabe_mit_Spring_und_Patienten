package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Position;
import com.example.demo.entity.Verordnung;
import com.example.demo.repository.VerordnungRepository;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
    static String DATE_FORMAT = "yyyy.MM.dd";
    @Autowired
    VerordnungRepository verordnungRepository;

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

    /**
     * checks account to have valid data for admin account
     * @param username
     * @param pw
     * @return enum with options
     */
    public static IsAdmin isAdminAccount (String username, String pw){
        if(isParameterEmpty(username, pw)) {
            return IsAdmin.EMPTY_PARAMETER;
        } else if (username.equals("admin") && pw.equals("secret")) {
            return IsAdmin.YES;
        } else {
            return IsAdmin.WRONG_PARAMETER;
        }
    }

    /**
     * helps to check for admin account
     * @param username
     * @param pw
     * @return
     */
    static boolean isParameterEmpty (String username, String pw) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(pw);
    }

    private static final int countCharsFor1Tab = 5;

    private static final int countCharsFor2Tabs = 10;

    public static List<Verordnung> createNewData() {
        return createNewData(COUNT_DATA);
    }

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

    static long getYearInMilliseconds(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        int daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        return TimeUnit.DAYS.toMillis(daysInYear);
    }

    static Verordnung createNewVerordnung(Patient patient, Position position){
        Verordnung verordnung = new Verordnung();
        verordnung.setBelegnummer(String.valueOf(Tools.getRandomNumberWithLength(2)));
        verordnung.setAusstellungsdatum(
                LocalDateTime.now().plusYears(1l)
        );
        verordnung.setKostentraeger(
                new Faker().address().cityName()
        );
        verordnung.setBetriebsstaettennummer(
                String.valueOf(Tools.getRandomNumberWithLength(9))
        );
        verordnung.setVertragsarztnummer(
                String.valueOf(Tools.getRandomNumberWithLength(9))
        );
        verordnung.setPatient_id(patient);
        verordnung.setPosition_id(position);
        verordnung.setDatum_erstellt(LocalDateTime.now());
        return verordnung;
    }


    Instant now = Instant.now();

    private final static LocalDate date = LocalDate.now(ZoneId.of("Europe/Berlin"));

    static Position createNewPosition(){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        Position position = new Position();
        position.setPositionsnummer(
                String.valueOf(Tools.getRandomNumberWithLength(20))
        );
        String text = RandomText.getText();
        position.setPositionstext(
                text.length() <= 100
                        ? text
                        : text.substring(0, 99)
        );
        // Calculate minus 2 for position with commata
        position.setEinzelpreis(
                String.format("%.02f",
                        //getRandomFloatWithLength(9-2))
                        Tools.getRandomFloatWithLength(
                                Tools.getRandomNumberWithMaxLength(9)-2))
        );
        // Calculate minus 2 for position with commata
        position.setMenge(
                String.format("%.02f",
                        Tools.getRandomFloatWithLength(
                                Tools.getRandomNumberWithMaxLength(6)-2))
        );
        // Calculate minus 2 for position with commata
        position.setMehrwertsteuersatz(
                String.format("%.02f",
                        Tools.getRandomFloatWithLength(
                                Tools.getRandomNumberWithMaxLength(4)-2))
        );
        return position;
    }

    static Patient createNewPatient() {
        Faker faker = new Faker();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String street = faker.address().streetName();
        //String plz = String.valueOf(10000  + new Random().nextInt(90000));
        String plz = String.valueOf(Tools.getRandomNumberWithLength(5));
        String city = faker.address().cityName();
        int randomDay = ThreadLocalRandom.current().nextInt(1, 30 + 1);
        int randomMonth = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        // Regel: Geburtsdatum nicht vor Ausstellungsdatum der Verordnung
        int randomYear = LocalDate.now().getYear() - ThreadLocalRandom.current().nextInt(1, 100 + 1) + 2;
        LocalDate birthdate = LocalDate.of(randomYear, randomMonth, randomDay);
        //String randomNumber = String.valueOf(100000000  + new Random().nextInt(900000000));
        String randomNumber = String.valueOf(Tools.getRandomNumberWithLength(9));
        Patient patient = new Patient();
        patient.setVorname(
                firstname);
        patient.setNachname(
                lastname);
        patient.setGeburtsdatum(
                birthdate);
        patient.setVersichertennummer(
                randomNumber);
        patient.setStrasse(
                street);
        patient.setPLZ(
                plz);
        patient.setOrt(
                city);
        return patient;
    }

    /**
     * @param verordnungList
     * @return list with objects of type Verordnung
     */
    public static List<Verordnung> getDataWithoutSensibleInfos(List<Verordnung> verordnungList){
        return getDataWithoutSensibleInfos(false, verordnungList);
    }

    /**
     *
     * @param isAdmin
     *  enables filter for getting more (sensible) data
     * @param verordnungList
     * @return list with objects of type Verordnung
     */
    public static List<Verordnung> getDataWithoutSensibleInfos(boolean isAdmin, List<Verordnung> verordnungList){
        return verordnungList;
    }

    /**
     *
     * @param verordnungList
     * @return true if data was saved, false otherwise
     */
    public static boolean saveData(List<Verordnung> verordnungList){
        return true;
    }

    /**
     *
     * @param isAdmin
     * @param verordnungList
     * @return true if data was saved, false otherwise
     */
    public static boolean saveData(boolean isAdmin, List<Verordnung> verordnungList){
        return true;
    }

    public static enum IsDataValid {
        YES(""),
        AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM("Fehler: Ausstellungsdatum der Verordnung liegt zeitlich vor dem Einreichungsdatum."),
        GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM("Fehler: Geburtsdatum liegt zeitlich vor dem Ausstellungsdatum der Verordnung."),
        BELEG_MEHRFACH_VORHANDEN("Fehler: Beleg ist mehrfach vorhanden. Belegnummer muss stets eindeutig je Kundennummer sein"),
        SOMETHING_ELSE("Something really strange happens here."),
        EMPTY_PARAMETER("Fehler: Eingabeparameter ist leer.");
        String text;
        IsDataValid(String text){
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    public IsDataValid isDataValid(String ausstellungsdatum, String einreichungsdatum, String geburtstag, Verordnung verordnung){
        return isDataValid(
                ausstellungsdatum,
                einreichungsdatum,
                geburtstag,
                verordnung.getBelegnummer()
        );
    }

    public IsDataValid isDataValid(Verordnung verordnung){
        return isDataValid(
                localdatetimeToString(
                        verordnung
                                .getAusstellungsdatum()),
                localdatetimeToString(
                        instantToLocaldatetime(
                                Instant.now())),
                localdateToString(
                        verordnung
                                .getPatient_id()
                                .getGeburtsdatum()),
                verordnung.getBelegnummer()
        );
    }

    public IsDataValid isDataValid(String ausstellungsdatum, String einreichungsdatum, String geburtstag, String belegnummer){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dt_ausstelungsdatum = LocalDateTime.parse(ausstellungsdatum, formatter);
        LocalDateTime dt_einreichungsdatum = LocalDateTime.parse(einreichungsdatum, formatter);
        LocalDateTime dt_geburtstag = LocalDateTime.parse(geburtstag, formatter);
        // Get all verordnung -> belegnummern
        if(StringUtils.isNotBlank(belegnummer)) {
            Optional<Verordnung> optionalVerordnung = verordnungRepository.findVerordnungByBelegnummer(belegnummer);
            if (optionalVerordnung.isPresent() && ! optionalVerordnung.stream().toList().isEmpty()) {
                return IsDataValid.BELEG_MEHRFACH_VORHANDEN;
            }
        }

        if(dt_ausstelungsdatum.isBefore(dt_einreichungsdatum)){
            return IsDataValid.AUSSTELLUNGSDATUM_VOR_EINREICHUNGSDATUM;
        } else if(dt_geburtstag.isBefore(dt_ausstelungsdatum)){
            return IsDataValid.GEBURTSDATUM_VOR_AUSSTELLUNGSDATUM;
        } else {
            return IsDataValid.SOMETHING_ELSE;
        }
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

    static String localdatetimeToString(LocalDateTime ldt){
        return ldt.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    static String localdateToString(LocalDate ld){
        return ld.format(formatter);
    }

    static LocalDateTime instantToLocaldatetime(Instant instant){
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
