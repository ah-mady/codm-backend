package com.codmtracker.service;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(AuthRequestDto request);

    void sendEmailConfirmation(String email);

    void confirmEmail(String token);

    void sendPasswordReset(String email);

    void resetPassword(String token, String newPassword);
}
