package com.bougsid.transport;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;

/**
 * Created by ayoub on 7/24/2016.
 */
@Entity
@Scope("prototype")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransportType transportType;
    private double Taux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public double getTaux() {
        return Taux;
    }

    public void setTaux(double taux) {
        Taux = taux;
    }
}
