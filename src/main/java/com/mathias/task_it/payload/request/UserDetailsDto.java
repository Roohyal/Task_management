package com.mathias.task_it.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDetailsDto {
    private  String firstName;
    private  String lastName;
    private  String email;
}
