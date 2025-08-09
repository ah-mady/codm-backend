package com.codmtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${codm.frontend.url}")
    private String frontendUrl;

    @Override
    public void sendEmailConfirmation(String email, String token) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Confirm your CODM Tracker Email");
        String url = frontendUrl + "/confirm?token=" + token;
        msg.setText("Please confirm your email with this link: " + url);
        mailSender.send(msg);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Reset your CODM Tracker Password");
        String url = frontendUrl + "/reset?token=" + token;
        msg.setText("Reset your password here: " + url);
        mailSender.send(msg);
    }

    @Override
    public void sendTeamInviteEmail(String email, String token, String teamName) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("You are invited to join team: " + teamName);
        String url = frontendUrl + "/invites/accept?token=" + token;
        msg.setText("You have been invited to join team " + teamName + ". Accept here: " + url);
        mailSender.send(msg);
    }
}
