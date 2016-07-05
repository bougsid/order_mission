package com.bougsid.grade;

/**
 * Created by ayoub on 7/3/2016.
 */
public enum GradeType {
    DG("DG"), DA("DA"),CHEF("CHEF"),AUTRE("AUTRE");

    private String label;

    GradeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
