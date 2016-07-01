package com.bougsid.service;

import com.bougsid.employe.Employe;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ayoub on 6/30/2016.
 */
@Entity
@Scope("prototype")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToOne
    private Employe chef;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    private List<Employe> membres;

    public Service() {
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

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }

    public List<Employe> getMembres() {
        return membres;
    }

    public void setMembres(List<Employe> membres) {
        this.membres = membres;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
