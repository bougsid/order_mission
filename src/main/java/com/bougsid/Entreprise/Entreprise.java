package com.bougsid.entreprise;

import com.bougsid.mission.Mission;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
@Entity
@Scope("prototype")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "entreprise")
    private List<Mission> missions;
    public Entreprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString(){
        return String.valueOf(id);
    }
}
