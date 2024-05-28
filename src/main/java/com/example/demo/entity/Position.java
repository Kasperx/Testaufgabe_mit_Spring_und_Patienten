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
    int id;

    @Column(name = "Positionsnummer", length = 20)
    String Positionsnummer;

    @Column(name = "Positionstext")
    @Size(max = 100)
    String Positionstext;

    //@Column(name = "Einzelpreis", length = 6)
    //

    @Column(name = "Einzelpreis")
    @Size(max = 10) // the dot of the decimal number has one position
    String Einzelpreis;

    //@Column(name = "Menge", length = 6)
    //float Menge;

    @Column(name = "Menge")
    @Size(max = 7) // the dot of the decimal number has one position
    String Menge;

    //@Column(name = "Mehrwertsteuersatz", length = 4)
    //float Mehrwertsteuersatz;

    @Column(name = "Mehrwertsteuersatz")
    @Size(max = 5) // the dot of the decimal number has one position
    String Mehrwertsteuersatz;

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
}
