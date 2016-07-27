package com.bougsid.statistics;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * Created by ayoub on 7/25/2016.
 */
public enum DateType {
    ALL("Tous", null),
    THISMONTH("Ce mois-ci", ChronoField.DAY_OF_MONTH),
    THISYEAR("Cette année", ChronoField.DAY_OF_YEAR),
    CUSTOM("Période personnalisée");
    private String label;
    private LocalDate start;
    private LocalDate end;

    DateType(String label, ChronoField ch) {
        this.label = label;
            this.end = LocalDate.now();
        if (ch != null) {
            this.start = end.with(ch, 1);
        } else {
            this.start = LocalDate.MIN;
        }
        System.out.println("sart ? " + start);
        System.out.println("sart ? " + end);
    }

    DateType(String label) {
        this.label = label;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getLabel() {
        return label;
    }
}
