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

    private double tauxDejounerDiner;
    private double tauxPetitDejouner;
    private double tauxHebergement;
    private double tauxKilometrique;

    private double tauxDejounerDinerDirec;
    private double tauxPetitDejounerDirec;
    private double tauxHebergementDirec;
    private double tauxKilometriqueDirec;

    private double tauxTaxi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTauxDejounerDiner() {
        return tauxDejounerDiner;
    }

    public void setTauxDejounerDiner(double tauxDejounerDiner) {
        this.tauxDejounerDiner = tauxDejounerDiner;
    }

    public double getTauxPetitDejouner() {
        return tauxPetitDejouner;
    }

    public void setTauxPetitDejouner(double tauxPetitDejouner) {
        this.tauxPetitDejouner = tauxPetitDejouner;
    }

    public double getTauxHebergement() {
        return tauxHebergement;
    }

    public void setTauxHebergement(double tauxHebergement) {
        this.tauxHebergement = tauxHebergement;
    }

    public double getTauxKilometrique() {
        return tauxKilometrique;
    }

    public void setTauxKilometrique(double tauxKilometrique) {
        this.tauxKilometrique = tauxKilometrique;
    }

    public double getTauxDejounerDinerDirec() {
        return tauxDejounerDinerDirec;
    }

    public void setTauxDejounerDinerDirec(double tauxDejounerDinerDirec) {
        this.tauxDejounerDinerDirec = tauxDejounerDinerDirec;
    }

    public double getTauxPetitDejounerDirec() {
        return tauxPetitDejounerDirec;
    }

    public void setTauxPetitDejounerDirec(double tauxPetitDejounerDirec) {
        this.tauxPetitDejounerDirec = tauxPetitDejounerDirec;
    }

    public double getTauxHebergementDirec() {
        return tauxHebergementDirec;
    }

    public void setTauxHebergementDirec(double tauxHebergementDirec) {
        this.tauxHebergementDirec = tauxHebergementDirec;
    }

    public double getTauxKilometriqueDirec() {
        return tauxKilometriqueDirec;
    }

    public void setTauxKilometriqueDirec(double tauxKilometriqueDirec) {
        this.tauxKilometriqueDirec = tauxKilometriqueDirec;
    }

    public double getTauxTaxi() {
        return tauxTaxi;
    }

    public void setTauxTaxi(double tauxTaxi) {
        this.tauxTaxi = tauxTaxi;
    }
}
