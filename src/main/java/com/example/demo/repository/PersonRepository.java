package com.example.demo.repository;

import com.example.demo.entity.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//public interface PersonRepository extends CrudRepository<Person, Integer> {
@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Integer> {

    boolean existsPersonByEmail(String email);
    /*
    @Query(
            value = "select * from Person",
            nativeQuery = true
    )
    List<Person> findAllWithPermission();
    */

    @Query(
            //value = "select firstname, lastname, age from Person where isAdmin is false",
            value = "select firstname, lastname, age from Person where isAdmin is false",
            nativeQuery = true
    )
    List<Object> findAllWithoutPermission();

    @Query(
            value = "select * from Person where isAdmin is true",
            nativeQuery = true
    )
    List<Person> findAdmin();
}
