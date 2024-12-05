package com.mathias.task_it.service.impl;

import com.mathias.task_it.domain.entities.ConfirmationTokenModel;
import com.mathias.task_it.domain.entities.Person;
import com.mathias.task_it.repository.ConfirmationTokenRepository;
import com.mathias.task_it.repository.PersonRepository;
import com.mathias.task_it.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PersonRepository personRepository;

    @Override
    public String validateToken(String token) {
        Optional<ConfirmationTokenModel> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isEmpty()) {
            return "Invalid token";
        }
        ConfirmationTokenModel confirmationToken = confirmationTokenOptional.get();
        if (confirmationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token has expired";
        }
        Person person = confirmationToken.getPersons();
        person.setEnabled(true);
        personRepository.save(person);

        confirmationTokenRepository.delete(confirmationToken);

        return "Your Email has been Confirmed Succesfully";
    }
}
