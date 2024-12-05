package com.mathias.task_it.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePersonRequest {
    private String firstName;
    private String lastName;

}
