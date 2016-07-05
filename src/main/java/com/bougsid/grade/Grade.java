package com.bougsid.grade;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;

/**
 * Created by ayoub on 7/3/2016.
 */
@Entity
@Scope("prototype")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GradeType type;
    private String label;

    public Grade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Grade) && (id != null)
                ? id.equals(((Grade) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }
}
