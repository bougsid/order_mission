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
import java.time.format.DateTimeFormatter;
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

        List<Employe> responsables = this.employeService.getResponsables(mission);
        if (responsables == null || responsables.size() == 0) return;
        for (Employe employe : responsables) {
            if (employe.getEmail() == null) continue;
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(employe.getEmail());
            System.out.println("To : " + employe.getEmail());
            helper.setFrom("a.bougsid@gmail.com");
            helper.setSubject(msg.getMessage("mission.compose") +" "+mission.getObjet()+ " - "+ mission.getEmploye().getCivilite() + " " + mission.getEmploye().getFullName() );
            helper.setText("<html><body><h3>"
                            + mission.getEmploye().getCivilite() + " " + mission.getEmploye().getFullName() + "<br/>"
                            + msg.getMessage("mission.objet") + ": " + mission.getObjet() + "<br/>"
                            + msg.getMessage("mission.startDate") + ": " + mission.getStartDate().format(DateTimeFormatter.ofPattern(msg.getMessage("date.pattern"))) + "<br/>"
                            + msg.getMessage("mission.endDate") + ": " + mission.getEndDate().format(DateTimeFormatter.ofPattern(msg.getMessage("date.pattern"))) + "<br/>"
                            + msg.getMessage("mission.destination") + ": " + mission.getStringfyVille() + "<br/>"
                            + msg.getMessage("mission.transporttype") + ": " + mission.getTransportType().getLabel() + "<br/>"
//                            + msg.getMessage("mission.entreprise") + ": " + mission.getEntreprise().getNom() + "<br/>"
                            + msg.getMessage("mission.type") + ": " + mission.getType().getLabel() + "<br/>"
//                            + msg.getMessage("mission.objet") + ": " + mission.getObjet() + "<br/>"
//                            + msg.getMessage("mission.objet") + ": " + mission.getObjet() + "<br/>"
                            + "<a href='" + msg.getMessage("application.baseurl") + "confirm?mission=" + mission.getUuid() + "&s=" + mission.getSecret() + "'>" +
                            "Confirmer</a>"
                            + "    <a href='" + msg.getMessage("application.baseurl") + "reject?mission=" + mission.getUuid() + "&s=" + mission.getSecret() + "'>" +
                            "Refuser</a></h3>" +
                            "</body></html>"
                    , true);
            javaMailSender.send(mail);
        }

        System.out.println("Email Sent!");
    }

}