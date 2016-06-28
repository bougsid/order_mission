package com.bougsid.employe;

/**
 * Created by ayoub on 6/25/2016.
 */
public enum EmployeClasse {
    A("A"),B("B"),C("C");
    private String label;

    EmployeClasse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
