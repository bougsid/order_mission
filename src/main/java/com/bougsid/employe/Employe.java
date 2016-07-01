package com.bougsid.employe;

import com.bougsid.bank.Bank;
import com.bougsid.mission.Mission;
import com.bougsid.service.Service;
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
    @Enumerated(EnumType.STRING)
    private Civilite civilite;
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private String fonction;
    private String grade;
    @Enumerated(EnumType.STRING)
    private EmployeClasse classe;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service", nullable = true)
    private Service service;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employe")
    private List<Mission> missions;
    @Enumerated(EnumType.STRING)
    private EmployeRole hierarchie;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "APP_USER_USER_PROFILE",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_PROFILE_ID")})
    private Set<EmployeProfile> employeProfiles = new HashSet<EmployeProfile>();

    @Enumerated(EnumType.STRING)
    private EmployeRole role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bank")
    private Bank bank;
    private String rib;

    public Employe() {

    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
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

    public EmployeRole getRole() {
        return role;
    }

    public void setRole(EmployeRole role) {
        this.role = role;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Civilite getCivilite() {
        return civilite;
    }


    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public EmployeRole getHierarchie() {
        return hierarchie;
    }

    public void setHierarchie(EmployeRole hierarchie) {
        this.hierarchie = hierarchie;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Transient
    public String getFullName() {
        return this.nom + " " + this.prenom;
    }

    @Override
    public String toString() {
        return String.valueOf(idEmploye);
    }
}
