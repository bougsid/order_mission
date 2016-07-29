package com.bougsid.ville;

import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by ayoub on 7/1/2016.
 */
@Entity
@Scope("prototype")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double distance;
    private double tauxAuto;
    private double tauxCTM;
    private double tauxTRAIN;
    private double tauxAvion;
    private long nombreTickAuto;

    public Ville() {
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTauxAuto() {
        return tauxAuto;
    }

    public void setTauxAuto(double tauxAuto) {
        this.tauxAuto = tauxAuto;
    }

    public double getTauxCTM() {
        return tauxCTM;
    }

    public void setTauxCTM(double tauxCTM) {
        this.tauxCTM = tauxCTM;
    }

    public double getTauxTRAIN() {
        return tauxTRAIN;
    }

    public void setTauxTRAIN(double tauxTRAIN) {
        this.tauxTRAIN = tauxTRAIN;
    }

    public double getTauxAvion() {
        return tauxAvion;
    }

    public void setTauxAvion(double tauxAvion) {
        this.tauxAvion = tauxAvion;
    }

    public long getNombreTickAuto() {
        return nombreTickAuto;
    }

    public void setNombreTickAuto(long nombreTickAuto) {
        this.nombreTickAuto = nombreTickAuto;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Ville) && (id != null)
                ? id.equals(((Ville) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }
}
