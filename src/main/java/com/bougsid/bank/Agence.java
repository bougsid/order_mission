package com.bougsid.bank;

import com.bougsid.employe.Employe;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 7/28/2016.
 */
@Entity
@Scope("prototype")
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bank")
    private Bank bank;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agence")
    private Set<Employe> employes = new HashSet<>();

    public Agence(String nom,Bank bank) {
        this.nom = nom;
        this.bank = bank;
    }

    public Agence() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Set<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(Set<Employe> employes) {
        this.employes = employes;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Agence) && (id != null)
                ? id.equals(((Agence) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }
    @Transactional
    public String getFullName(){
        return bank.getName()+". "+nom;
    }
}
