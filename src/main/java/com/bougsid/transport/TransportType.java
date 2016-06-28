package com.bougsid.transport;

/**
 * Created by ayoub on 6/25/2016.
 */
public enum TransportType {
    PERSONNEL("Personnel"),Service("Service");

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
