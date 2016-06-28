package com.bougsid.employe;

/**
 * Created by ayoub on 6/26/2016.
 */
public enum EmployeRole {
    DG("DG"),DE("DE"),EMP("EMP"),SAE("SAE"),LEC("LEC");

    private String label;

    EmployeRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
