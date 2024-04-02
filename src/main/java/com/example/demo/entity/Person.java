package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import static com.example.demo.service.PersonService.getPasswordString;
import static com.example.demo.service.PersonService.getTabsForConsoleOut;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PERSON")
public class Person {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    public Person(String firstName, String lastName, String email, String password, String birthdata, boolean isAdmin) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.birthdate = birthdata;
        this.isAdmin = isAdmin;
    }

    public Person(String firstName, String lastName, String birthdata) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.birthdate = birthdata;
    }

    @Column(name = "FIRSTNAME")
    String firstname;

    @Column(name = "LASTNAME")
    String lastname;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PASSWORD")
    String password;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "BIRTHDATE")
    String birthdate;

    @Column(name = "ISADMIN")
    boolean isAdmin;

    /*
    @Override
    public String toString() {
        return "Person{" +
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
    final boolean showLengthOfText = false;
    public String toString() {
        if(showLengthOfText){
            return "Person{" +
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
            return "Person{" +
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
}
