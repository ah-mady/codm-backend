package com.codmtracker.service;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;

public interface AuthService {
    void signup(AuthRequestDto request);

    AuthResponseDto login(AuthRequestDto request);

    void confirmEmail(String token);

    void requestPasswordReset(String email);

    void resetPassword(String token, String newPassword);
}
