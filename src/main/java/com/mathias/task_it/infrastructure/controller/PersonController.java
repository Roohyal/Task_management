package com.mathias.task_it.infrastructure.controller;

import com.mathias.task_it.payload.request.UpdatePersonRequest;
import com.mathias.task_it.payload.request.UserDetailsDto;
import com.mathias.task_it.payload.response.UpdatePersonResponse;
import com.mathias.task_it.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService personService;

    @PutMapping
    public ResponseEntity<?> updatePerson(@RequestParam UpdatePersonRequest updatePersonRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UpdatePersonResponse response = personService.updatePersonDetails(updatePersonRequest,currentUsername);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getSignedInUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserDetailsDto response = personService.getPersonDetails(currentUsername);
        return ResponseEntity.ok(response);
    }

}
