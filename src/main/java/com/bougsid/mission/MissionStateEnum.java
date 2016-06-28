package com.bougsid.mission;

/**
 * Created by ayoub on 6/27/2016.
 */
public enum MissionStateEnum {
    CURRENT("En cours"),
    VDE("Valider par le directeur d'etude"),
    VDG("Valider pa le directeur Générale"),
    VSAE("Valider par service d'entreprise"),
    VLEC("Valider par LEC"),
    RDE("Refuser par le directeur d'etude"),
    RDG("Refuser par le directeur generale"),
    RSAE("Refuser par service d'entreprise"),
    RLEC("Refuser par LEC");
    private String label;

    MissionStateEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
