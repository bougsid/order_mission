package com.bougsid.mission;

import com.bougsid.employe.Employe;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.missionType.MissionType;
import com.bougsid.transport.Transport;
import com.bougsid.ville.Ville;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ayoub on 6/23/2016.
 */
@Scope("prototype")
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMission;
    private String objet;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
//    private String destination;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Transport transport;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MISSION_MISSION_STATE",
            joinColumns = {@JoinColumn(name = "MISSION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MISSION_STATE_ID")})
    private Set<MissionState> states = new HashSet<MissionState>();
    @Enumerated(EnumType.STRING)
    private MissionStateEnum currentState;
    @Enumerated(EnumType.STRING)
    private MissionStateEnum nextState;
    @OneToOne
    @JoinColumn(name = "id_type")
    private MissionType type;
    private String comment;
    private String uuid;
//    @OneToMany(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MISSION_VILLE",
            joinColumns = {@JoinColumn(name = "MISSION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "VILLE_ID")})
    private List<Ville> villes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = false)
    private Entreprise entreprise;

    public Mission() {
        this.transport = new Transport();
        this.uuid = UUID.randomUUID().toString();
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Long getIdMission() {
        return idMission;
    }

    public void setIdMission(Long idMission) {
        this.idMission = idMission;
    }

    public Set<MissionState> getStates() {
        return states;
    }

    public void setStates(Set<MissionState> states) {
        this.states = states;
    }

    public void addState(MissionState state) {
        this.states.add(state);
    }

    public MissionStateEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MissionStateEnum currentState) {
        this.currentState = currentState;
    }

    public MissionStateEnum getNextState() {
        return nextState;
    }

    public void setNextState(MissionStateEnum nextState) {
        this.nextState = nextState;
    }

    public MissionType getType() {
        return type;
    }

    public void setType(MissionType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Transient
    public MissionState getLastState() {
        return Collections.max(this.states, Comparator.comparing(MissionState::getStateDate));
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    @Transient
    public void addVille(Ville ville) {
        this.villes.add(ville);
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "idMission=" + idMission +
                ", objet='" + objet + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", transport=" + transport +
                ", employe=" + employe +
                '}';
    }
}
