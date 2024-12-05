package com.mathias.task_it.service;

import com.mathias.task_it.payload.request.*;
import com.mathias.task_it.payload.response.LoginResponse;
import com.mathias.task_it.payload.response.PersonRegisterResponse;
import com.mathias.task_it.payload.response.UpdatePersonResponse;
import jakarta.mail.MessagingException;

public interface PersonService {
    PersonRegisterResponse registerPerson(PersonRegisterRequest personRegisterRequest) throws Exception;

    LoginResponse login(LoginRequest loginRequest);

    String forgotPassword(ForgetPasswordRequestDto forgetpassword) throws MessagingException;

    String resetPassword(ResetPasswordRequestDto resetpassword);

    UpdatePersonResponse updatePersonDetails(UpdatePersonRequest updatePersonRequest, String email) throws Exception;

    UserDetailsDto getPersonDetails(String email) throws Exception;
}
