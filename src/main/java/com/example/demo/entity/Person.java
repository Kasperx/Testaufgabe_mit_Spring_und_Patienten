package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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

    public Person(String firstName, String lastName, String email, String password, int age, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.isAdmin = isAdmin;
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Column(name = "FIRSTNAME")
    String firstName;

    @Column(name = "LASTNAME")
    String lastName;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "AGE")
    int age;

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
    /*
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName("+firstName.length()+")='" + firstName + '\'' +
                (firstName.length() < countCharsFor1Tab
                        ? ", \t-\t-\t"
                        : (firstName.length() < countCharsFor2Tabs
                                ? ", \t-\t"
                                : "\t" )
                ) +
                "lastName("+lastName.length()+")='" + lastName + '\'' +
                (lastName.length() < countCharsFor1Tab
                        ? ", \t-\t-\t"
                        : (lastName.length() < countCharsFor2Tabs
                                ? ", \t-\t"
                                : "\t" )
                ) +
                "email("+email.length()+")='" + email + '\'' +
                (email.length() < countCharsFor1Tab
                        ? ", \t-\t-\t"
                        : (email.length() < countCharsFor2Tabs
                                ? ", \t-\t"
                                : "\t" )
                ) +
                "password("+password.length()+")='" + password + '\'' +
                ", \tage=" + age +
                ", \tisAdmin=" + isAdmin +
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
                    "age=" + age +
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
                    "age=" + age +
                    ", isAdmin=" + isAdmin +
                    '}';
        }
    }
}
