package com.bougsid.mission;

/**
 * Created by ayoub on 6/27/2016.
 */
public enum MissionStateEnum {
    CURRENT("En cours"),
    VCHEF("Valider par le chef de service"),
    VDG("Valider pa le directeur Générale"),
    VDTYPE("Valider par service d'entreprise"),
    RCHEF("Refuser par le chef de service"),
    RDG("Refuser par le directeur generale"),
    RDTYPE("Refuser par service d'entreprise"),
    DAF("DAF"),
    VALIDATED("VALIDATED");

    private String label;

    MissionStateEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
