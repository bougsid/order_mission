package com.bougsid.decompte;

import com.bougsid.mission.Mission;
import com.bougsid.transport.TransportType;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;

/**
 * Created by ayoub on 7/24/2016.
 */
@Entity
@Scope("prototype")
public class Decompte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double distance;
    private double distanceAjouter;
    private long nombreTickAuto;
    private double tauxAuto;
    private double tauxDejounerDiner;
    private double tauxPetitDejouner;
    private double tauxHebergement;
    private double tauxKilometrique;
    private double tauxTaxi;
    private long days;
    private long dejounerDiner;
    private long petitDejouner;
    private long hebergement;
    private double kilometrique;
    private String depart;
    private double total;
    private String imputation;
    private String exercice;

    @OneToOne
    private Mission mission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistanceAjouter() {
        return distanceAjouter;
    }

    public void setDistanceAjouter(double distanceAjouter) {
        this.distanceAjouter = distanceAjouter;
    }

    public long getNombreTickAuto() {
        return nombreTickAuto;
    }

    public void setNombreTickAuto(long nombreTickAuto) {
        this.nombreTickAuto = nombreTickAuto;
    }

    public double getTauxAuto() {
        return tauxAuto;
    }

    public void setTauxAuto(double tauxAuto) {
        this.tauxAuto = tauxAuto;
    }

    public double getTauxDejounerDiner() {
        return tauxDejounerDiner;
    }

    public void setTauxDejounerDiner(double tauxDejounerDiner) {
        this.tauxDejounerDiner = tauxDejounerDiner;
    }

    public double getTauxPetitDejouner() {
        return tauxPetitDejouner;
    }

    public void setTauxPetitDejouner(double tauxPetitDejouner) {
        this.tauxPetitDejouner = tauxPetitDejouner;
    }

    public double getTauxHebergement() {
        return tauxHebergement;
    }

    public void setTauxHebergement(double tauxHebergement) {
        this.tauxHebergement = tauxHebergement;
    }

    public double getTauxKilometrique() {
        return tauxKilometrique;
    }

    public void setTauxKilometrique(double tauxKilometrique) {
        this.tauxKilometrique = tauxKilometrique;
    }

    public long getDejounerDiner() {
        return dejounerDiner;
    }

    public void setDejounerDiner(long dejounerDiner) {
        this.dejounerDiner = dejounerDiner;
    }

    public long getPetitDejouner() {
        return petitDejouner;
    }

    public void setPetitDejouner(long petitDejouner) {
        this.petitDejouner = petitDejouner;
    }

    public long getHebergement() {
        return hebergement;
    }

    public void setHebergement(long hebergement) {
        this.hebergement = hebergement;
    }

    public double getKilometrique() {
        return kilometrique;
    }

    public void setKilometrique(double kilometrique) {
        this.kilometrique = kilometrique;
    }

    public double getTauxTaxi() {
        return tauxTaxi;
    }

    public void setTauxTaxi(double tauxTaxi) {
        this.tauxTaxi = tauxTaxi;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Transient
    public void calculeTotal() {
        double pd = petitDejouner * tauxPetitDejouner;
        double dd = dejounerDiner * tauxDejounerDiner;
        double h = hebergement * tauxHebergement;
        double k = tauxKilometrique * distance;
        double a = 0d; //Auto
        double t = 0d; //Taxi
        if (mission.getTransportType() == TransportType.PERSONNEL) {
            a = tauxAuto * nombreTickAuto;
        } else if (mission.getTransportType() == TransportType.TRAIN
                || mission.getTransportType() == TransportType.CTM
                || mission.getTransportType() == TransportType.AVION) {
            t = tauxTaxi * days;
        }
        System.out.println(pd);
        System.out.println(dd);
        System.out.println(h);
        System.out.println(k);
        System.out.println(a);
        System.out.println(t);
        this.total = pd + dd + h + k + a + t;
        System.out.println(total);
    }
}
