package com.bougsid.statistics;

/**
 * Created by ayoub on 7/25/2016.
 */
public enum StatisticsType {
    ALL("Tous"),TYPE("Type"), SERVICE("Service"), VILLE("Ville"), ENTREPRISE("Entreprise");

    private String label;
    private Object criteria;

    StatisticsType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Object getCriteria() {
        return criteria;
    }

    public void setCriteria(Object criteria) {
        this.criteria = criteria;
    }
}
