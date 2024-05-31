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

    Optional<Verordnung> findVerordnungByBelegnummer(String belegnummer);
}
