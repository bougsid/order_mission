package com.bougsid.util;

import com.bougsid.MSG;
import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import com.bougsid.mission.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    private MSG msg;
    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendNotificaitoin(Mission mission) throws MailException, InterruptedException, MessagingException {

        System.out.println("Sending email...");
        List<Employe> directors = this.employeService.getDirectors();
        for (Employe employe : directors) {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail,true);
            helper.setTo(employe.getEmail());
            helper.setFrom("a.bougsid@gmail.com");
            helper.setSubject(mission.getObjet() + " - "
                    + mission.getEmploye().getNom() + " "
                    + mission.getEmploye().getPrenom());
            helper.setText("<html><body>"+mission.getObjet() +"<a href='"+msg.getMessage("application.baseurl")+"confirm?mission="+mission.getUuid()+"'>Confirmer</a></body></html>",true);
            javaMailSender.send(mail);
        }

        System.out.println("Email Sent!");
    }

}