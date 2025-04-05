package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    //SQL
    @Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param(value = "email") String email);

//    //JPQL
//    @Query(value = "select u from  User u where u.email = :email")
//    User findByEmail(@Param(value = "email") String email);

//    //JPA Specific
//    User findByEmailAndAgeAfterBirth(String email, Integer age, LocalDate birthDate);

}
