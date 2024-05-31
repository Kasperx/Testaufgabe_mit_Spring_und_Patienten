package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "POSITION")
public class Position {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "Positionsnummer", length = 20)
    private String positionsnummer;

    @Column(name = "Positionstext")
    @Size(max = 100)
    private String positionstext;

    //@Column(name = "Einzelpreis", length = 6)
    //

    @Column(name = "Einzelpreis")
    @Size(max = 10) // the dot of the decimal number has one position
    private String einzelpreis;

    @Column(name = "Menge")
    @Size(max = 7) // the dot of the decimal number has one position
    private String menge;

    @Column(name = "Mehrwertsteuersatz")
    @Size(max = 5) // the dot of the decimal number has one position
    private String mehrwertsteuersatz;
}
