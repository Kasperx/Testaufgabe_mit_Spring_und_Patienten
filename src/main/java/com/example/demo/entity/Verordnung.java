package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "VERORDNUNG")
public class Verordnung {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    int id;

    @Column(name = "Belegnummer", length = 10)
    String Belegnummer;

    @Column(name = "Ausstellungsdatum", length = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date Ausstellungsdatum;

    @Column(name = "Kostentraeger", length = 50)
    String Kostentraeger;

    //@Column(name = "Kostentraeger_IK")
    //String Kostentraeger_IK;

    @Column(name = "Betriebsstaettennummer", length = 9)
    String Betriebsstaettennummer;

    @Column(name = "Vertragsarztnummer", length = 9)
    String Vertragsarztnummer;

    //@Column(name = "Patient")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    Patient patient_id;

    //@Column(name = "Position")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    Position position_id;

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
