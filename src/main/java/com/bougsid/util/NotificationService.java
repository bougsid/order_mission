package com.bougsid.util;

import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import com.bougsid.mission.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 6/28/2016.
 */
@Service
public class NotificationService {

    private JavaMailSender javaMailSender;
    @Autowired
    private IEmployeService employeService;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendNotificaitoin(Mission mission) throws MailException, InterruptedException {

        System.out.println("Sending email...");
        List<Employe> directors = this.employeService.getDirectors();
        for (Employe employe : directors) {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(employe.getEmail());
            mail.setFrom("a.bougsid@gmail.com");
            mail.setSubject(mission.getObjet() + " - "
                    + mission.getEmploye().getNom() + " "
                    + mission.getEmploye().getPrenom());
            mail.setText(mission.toString());
            javaMailSender.send(mail);
        }

        System.out.println("Email Sent!");
    }

}