package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

//import static com.example.demo.service.ProgramService.getPasswordString;


@Entity
@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "PATIENT")
public class Patient {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Vorname", length = 30)
    String Vorname;

    @Column(name = "Nachname", length = 47)
    String Nachname;

    @Column(name = "Geburtsdatum", length = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    String Geburtsdatum;

    @Column(name = "Versichertennummer", length = 12)
    String Versichertennummer;

    //@Column(name = "Versichertenstatus")
    //String Versichertenstatus;

    @Column(name = "Strasse", length = 30)
    String Strasse;

    @Column(name = "PLZ", length = 10)
    String PLZ;

    @Column(name = "Ort", length = 50)
    String Ort;

    //@Column(name = "Laenderkennzeichen")
    //String Laenderkennzeichen;

    /*
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", isAdmin=" + isAdmin +
                '}';
    }
     */
    /*
    final boolean showLengthOfText = false;
    public String toString() {
        if(showLengthOfText){
            return "Patient{" +
                    "id=" + id +
                    ", firstName(" + firstname.length() + ")='" + firstname + '\'' +
                    ", " + getTabsForConsoleOut(firstname) +
                    "lastName(" + lastname.length() + ")='" + lastname + '\'' +
                    ", " + getTabsForConsoleOut(lastname) +
                    "email(" + email.length() + ")='" + email + '\'' +
                    ", " + getTabsForConsoleOut(email) +
                    "password(" + getPasswordString(password).length() + ")='" + getPasswordString(password) + '\'' +
                    ", " + getTabsForConsoleOut(
                            getPasswordString(password)) +
                    "birthdata=" + birthdate +
                    ", isAdmin=" + isAdmin +
                    '}';
        } else {
            return "Patient{" +
                    "id=" + id +
                    ", firstName='" + firstname + '\'' +
                    ", " + getTabsForConsoleOut(firstname) +
                    "lastName='" + lastname + '\'' +
                    ", " + getTabsForConsoleOut(lastname) +
                    "email='" + email + '\'' +
                    ", " + getTabsForConsoleOut(email) +
                    "password='" + getPasswordString(password) + '\'' +
                    ", " + getTabsForConsoleOut(
                        getPasswordString(password)) +
                    "birthdata=" + birthdate +
                    ", isAdmin=" + isAdmin +
                    '}';
        }
    }
     */
    public Patient(String vorname, String nachname, String geburtsdatum, String versichertennummer, String strasse, String PLZ, String ort) {
        Vorname = vorname;
        Nachname = nachname;
        Geburtsdatum = geburtsdatum;
        Versichertennummer = versichertennummer;
        Strasse = strasse;
        this.PLZ = PLZ;
        Ort = ort;
    }
}
