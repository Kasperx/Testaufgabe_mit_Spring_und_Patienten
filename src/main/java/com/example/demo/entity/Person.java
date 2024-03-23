package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

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
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthdata = birthdata;
        this.isAdmin = isAdmin;
    }

    public Person(String firstName, String lastName, String birthdata) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdata = birthdata;
    }

    @Column(name = "FIRSTNAME")
    String firstName;

    @Column(name = "LASTNAME")
    String lastName;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PASSWORD")
    String password;

    @DateTimeFormat(pattern = "dd:MM:yyyy")
    @Column(name = "BIRTHDATE")
    String birthdata;

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
                    ", firstName(" + firstName.length() + ")='" + firstName + '\'' +
                    ", " + getTabsForConsoleOut(firstName) +
                    "lastName(" + lastName.length() + ")='" + lastName + '\'' +
                    ", " + getTabsForConsoleOut(lastName) +
                    "email(" + email.length() + ")='" + email + '\'' +
                    ", " + getTabsForConsoleOut(email) +
                    "password(" + password.length() + ")='" + password + '\'' +
                    ", " + getTabsForConsoleOut(password) +
                    "birthdata=" + birthdata +
                    ", isAdmin=" + isAdmin +
                    '}';
        } else {
            return "Person{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", " + getTabsForConsoleOut(firstName) +
                    "lastName='" + lastName + '\'' +
                    ", " + getTabsForConsoleOut(lastName) +
                    "email='" + email + '\'' +
                    ", " + getTabsForConsoleOut(email) +
                    "password='" + password + '\'' +
                    ", " + getTabsForConsoleOut(password) +
                    "birthdata=" + birthdata +
                    ", isAdmin=" + isAdmin +
                    '}';
        }
    }
}
