package com.bougsid.transport;

import com.bougsid.bank.Bank;
import com.bougsid.employe.Employe;
import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ayoub on 6/25/2016.
 */
@Scope("prototype")
@Entity
public class Vehicule {
    @Id
    @GeneratedValue
    private Long id;
    private String marque;
    private String matricule;

    private boolean service;

    @OneToOne
    private Employe employe;
    public Vehicule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

//    public TransportType getType() {
//        return type;
//    }
//
//    public void setType(TransportType type) {
//        this.type = type;
//    }

//    public Employe getAccompEmploye() {
//        return accompEmploye;
//    }
//
//    public void setAccompEmploye(Employe accompEmploye) {
//        this.accompEmploye = accompEmploye;
//    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }
    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Vehicule) && (id != null)
                ? id.equals(((Vehicule) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }
}
