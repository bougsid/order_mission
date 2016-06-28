package com.bougsid.employe;

import com.bougsid.mission.Mission;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ayoub on 6/23/2016.
 */
@Component
@Scope("prototype")
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;
    private String prenom;
    private String nom;
    private String matricule;
    private String fonction;
    private String grade;
    @Enumerated(EnumType.STRING)
    private EmployeClasse classe;
    private String password;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employe")
    private List<Mission> missions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "APP_USER_USER_PROFILE",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
    private Set<EmployeProfile> employeProfiles = new HashSet<EmployeProfile>();

    public Employe() {

    }

    public Long getIdEmploye() {
        System.out.println("---"+this);
        System.out.println("aaaaa"+idEmploye+"nom"+nom);
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        System.out.println("call setter"+idEmploye);
        this.idEmploye = idEmploye;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public EmployeClasse getClasse() {
        return classe;
    }

    public void setClasse(EmployeClasse classe) {
        this.classe = classe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<EmployeProfile> getEmployeProfiles() {
        return employeProfiles;
    }

    public void setEmployeProfiles(Set<EmployeProfile> employeProfiles) {
        this.employeProfiles = employeProfiles;
    }


}