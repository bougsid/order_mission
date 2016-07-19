package com.bougsid.missiontype;

import com.bougsid.service.Dept;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;

/**
 * Created by ayoub on 7/2/2016.
 */
@Entity
@Scope("prototype")
public class MissionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dept", nullable = true)
    private Dept dept;

    public MissionType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof MissionType) && (id != null)
                ? id.equals(((MissionType) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }
}

