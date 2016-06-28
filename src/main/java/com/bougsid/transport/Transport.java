package com.bougsid.transport;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;

/**
 * Created by ayoub on 6/25/2016.
 */
@Scope("prototype")
@Entity
public class Transport {
    @Id
    @GeneratedValue
    private Long id;
    private String marque;
    private String matricule;
    @Enumerated(EnumType.STRING)
    private TransportType type;

    public Transport() {
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

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", matricule='" + matricule + '\'' +
                ", type=" + type +
                '}';
    }
}
