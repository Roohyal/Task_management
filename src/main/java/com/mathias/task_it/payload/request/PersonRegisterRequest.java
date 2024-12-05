package com.mathias.task_it.payload.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRegisterRequest {
    @NotBlank(message = "Full name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email Format is Wrong ")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;


}