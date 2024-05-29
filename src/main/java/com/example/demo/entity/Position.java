package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "POSITION")
public class Position {

    final long serialId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "Positionsnummer", length = 20)
    private String Positionsnummer;

    @Column(name = "Positionstext")
    @Size(max = 100)
    private String Positionstext;

    //@Column(name = "Einzelpreis", length = 6)
    //

    @Column(name = "Einzelpreis")
    @Size(max = 10) // the dot of the decimal number has one position
    private String Einzelpreis;

    @Column(name = "Menge")
    @Size(max = 7) // the dot of the decimal number has one position
    private String Menge;

    @Column(name = "Mehrwertsteuersatz")
    @Size(max = 5) // the dot of the decimal number has one position
    private String Mehrwertsteuersatz;
}
