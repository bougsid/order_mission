package com.bougsid.bank;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 6/28/2016.
 */
@Entity
@Scope("prototype")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bank", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Agence> agences = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//
//    public Set<Employe> getEmployes() {
//        return employes;
//    }
//
//    public void setEmployes(Set<Employe> employes) {
//        this.employes = employes;
//    }

    public Set<Agence> getAgences() {
        return agences;
    }

    public void setAgences(Set<Agence> agences) {
        this.agences = agences;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Bank) && (id != null)
                ? id.equals(((Bank) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    public void addAgence(Agence agence) {
        agence.setBank(this);
        this.agences.add(agence);
        System.out.println("add Ageence");
    }
//    @Transient
//    public String agencesAsString() {
//        String agencesAsString = "";
//        for (Agence agence : agences) {
//            agencesAsString += agence.getNom() + "\n";
//        }
//        return agencesAsString;
//    }
}
