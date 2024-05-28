package com.example.demo.repository;

import com.example.demo.entity.Patient;
import com.example.demo.entity.Verordnung;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//public interface VerordnungRepository extends CrudRepository<Patient, Integer> {
@Repository
@Transactional
public interface VerordnungRepository extends JpaRepository<Verordnung, Integer> {

    /*
    @Query(
            value = "select * from Patient",
            nativeQuery = true
    )
    List<Patient> findAllWithPermission();
    */
    /*
    @Query(
            //value = "select firstname, lastname, age from Patient where isAdmin is false",
            value = "select firstname, lastname, age from PATIENT where isAdmin is false",
            nativeQuery = true
    )
    List<Object> findAllWithoutPermission();
     */
    /*
    @Query(
            value = "select * from PATIENT where isAdmin is true",
            nativeQuery = true
    )
     */
    //Optional<Patient> findByPatientVersichertennummer(String Versichertennummer);
}
