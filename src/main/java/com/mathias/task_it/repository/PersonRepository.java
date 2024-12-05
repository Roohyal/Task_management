package com.mathias.task_it.repository;

import com.mathias.task_it.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email); // To check if a user already exists by email

    Optional<Person> findByResetToken(String token);
}
