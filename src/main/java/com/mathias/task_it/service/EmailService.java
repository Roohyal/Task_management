package com.mathias.task_it.service;

import com.mathias.task_it.payload.request.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {


    void sendEmailAlert(EmailDetails emailDetails, String templateName) throws MessagingException;
}
