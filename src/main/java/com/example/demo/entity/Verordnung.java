package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalDate;

//@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VERORDNUNG")
public class Verordnung {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            unique = true)
    private int id;

    @CreatedDate
    @GeneratedValue
    @Column(name = "datum_erstellt",
            nullable = false,
            updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // Although required be program description ... "LocalDateTime" is recommended for spring and usage of "date" makes datetimeformat useless
    private LocalDateTime datum_erstellt;

    @LastModifiedDate
    @GeneratedValue
    @Column(name = "datum_bearbeitet")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // Although required be program description ... "LocalDateTime" is recommended for spring and usage of "date" makes datetimeformat useless
    private LocalDateTime datum_bearbeitet;

    @Column(name = "Belegnummer",
            length = 10)
    private String Belegnummer;

    @Column(name = "Ausstellungsdatum",
            length = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // Although required be program description ... "LocalDateTime" is recommended for spring and usage of "date" makes datetimeformat useless
    private LocalDateTime Ausstellungsdatum;

    @Column(name = "Kostentraeger")
    @Size(max = 50)
    private String Kostentraeger;

    @Column(name = "Betriebsstaettennummer")
    @Size(max = 9)
    private String Betriebsstaettennummer;

    @Column(name = "Vertragsarztnummer")
    @Size(max = 9)
    private String Vertragsarztnummer;

    //@Column(name = "Patient")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id",
            referencedColumnName = "id")
    private Patient patient_id;

    //@Column(name = "Position")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id",
            referencedColumnName = "id")
    private Position position_id;

    @Override
    public String toString() {
        return "Verordnung{" +
                "serialId=" + serialId +
                ", id=" + id +
                ", Belegnummer='" + Belegnummer + '\'' +
                ", Ausstellungsdatum=" + Ausstellungsdatum +
                ", Kostentraeger='" + Kostentraeger + '\'' +
                ", Betriebsstaettennummer='" + Betriebsstaettennummer + '\'' +
                ", Vertragsarztnummer='" + Vertragsarztnummer + '\'' +
                ", patient_id=" + patient_id +
                ", position_id=" + position_id +
                '}';
    }
}
