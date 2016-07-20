package com.bougsid.taux;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by ayoub on 7/20/2016.
 */
@Entity
public class Taux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float tauxDejounerDiner;
    private float tauxPetitDejouner;
    private float tauxHebergement;
    private float tauxKilometrique;

    private float tauxDejounerDinerDirec;
    private float tauxPetitDejounerDirec;
    private float tauxHebergementDirec;
    private float tauxKilometriqueDirec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTauxDejounerDiner() {
        return tauxDejounerDiner;
    }

    public void setTauxDejounerDiner(float tauxDejounerDiner) {
        this.tauxDejounerDiner = tauxDejounerDiner;
    }

    public float getTauxPetitDejouner() {
        return tauxPetitDejouner;
    }

    public void setTauxPetitDejouner(float tauxPetitDejouner) {
        this.tauxPetitDejouner = tauxPetitDejouner;
    }

    public float getTauxHebergement() {
        return tauxHebergement;
    }

    public void setTauxHebergement(float tauxHebergement) {
        this.tauxHebergement = tauxHebergement;
    }

    public float getTauxKilometrique() {
        return tauxKilometrique;
    }

    public void setTauxKilometrique(float tauxKilometrique) {
        this.tauxKilometrique = tauxKilometrique;
    }

    public float getTauxDejounerDinerDirec() {
        return tauxDejounerDinerDirec;
    }

    public void setTauxDejounerDinerDirec(float tauxDejounerDinerDirec) {
        this.tauxDejounerDinerDirec = tauxDejounerDinerDirec;
    }

    public float getTauxPetitDejounerDirec() {
        return tauxPetitDejounerDirec;
    }

    public void setTauxPetitDejounerDirec(float tauxPetitDejounerDirec) {
        this.tauxPetitDejounerDirec = tauxPetitDejounerDirec;
    }

    public float getTauxHebergementDirec() {
        return tauxHebergementDirec;
    }

    public void setTauxHebergementDirec(float tauxHebergementDirec) {
        this.tauxHebergementDirec = tauxHebergementDirec;
    }

    public float getTauxKilometriqueDirec() {
        return tauxKilometriqueDirec;
    }

    public void setTauxKilometriqueDirec(float tauxKilometriqueDirec) {
        this.tauxKilometriqueDirec = tauxKilometriqueDirec;
    }
}
