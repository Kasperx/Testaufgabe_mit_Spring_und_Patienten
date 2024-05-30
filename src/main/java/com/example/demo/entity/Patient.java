package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PATIENT")
public class Patient {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "Vorname", length = 30)
    private String Vorname;

    @Column(name = "Nachname", length = 47)
    private String Nachname;

    @Column(name = "Geburtsdatum", length = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // Although required be program description ... "LocalDate" is recommended for spring and usage of "date" makes datetimeformat useless
    private LocalDate Geburtsdatum;

    @Column(name = "Versichertennummer", length = 12)
    private String Versichertennummer;

    @Column(name = "Strasse", length = 30)
    private String Strasse;

    @Column(name = "PLZ", length = 10)
    private String PLZ;

    @Column(name = "Ort", length = 50)
    private String Ort;
}
