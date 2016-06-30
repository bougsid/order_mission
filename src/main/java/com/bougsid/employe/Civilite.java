package com.bougsid.employe;

/**
 * Created by ayoub on 6/30/2016.
 */
public enum Civilite {
    M("Monsieur"),MME("Madame");

    private String label;

    Civilite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
