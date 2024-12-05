package com.mathias.task_it.payload.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank( message = "Email is required")
    @Email( message = "Your Email Format is wrong ")
    private String email;

    @NotBlank( message = "Password is required")
    private String password;
}
