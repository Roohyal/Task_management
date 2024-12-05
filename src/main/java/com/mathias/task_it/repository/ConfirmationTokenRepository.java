package com.mathias.task_it.repository;

import com.mathias.task_it.domain.entities.ConfirmationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenModel, Long> {
    Optional<ConfirmationTokenModel> findByToken(String token);
}
