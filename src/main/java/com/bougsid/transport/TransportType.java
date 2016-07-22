package com.bougsid.transport;

import com.bougsid.employe.Employe;

/**
 * Created by ayoub on 6/25/2016.
 */
public enum TransportType {
    PERSONNEL("Personnel"),
    TRAIN("Train"),
    CTM("CTM"),
    Accompagnement("Accompagnement"),
    Service("Service"),
    AVION("Avion");

    private Employe employe;
    private String label;

    TransportType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
