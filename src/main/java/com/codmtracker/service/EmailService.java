package com.codmtracker.service;

public interface EmailService {
    void sendEmailConfirmation(String email, String token);

    void sendPasswordResetEmail(String email, String token);

    void sendTeamInviteEmail(String email, String token, String teamName);
}
