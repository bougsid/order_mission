package com.bougsid.employe;

import javax.persistence.*;

/**
 * Created by ayoub on 6/26/2016.
 */
@Entity
public class EmployeProfile {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private EmployeRole type= EmployeRole.USER;

    public EmployeProfile() {
    }

    public EmployeProfile(EmployeRole type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeRole getType() {
        return type;
    }

    public void setType(EmployeRole type) {
        this.type = type;
    }
}
