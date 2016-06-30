package com.bougsid.mission;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 6/27/2016.
 */
@Component
@Entity
@Scope("prototype")
public class MissionState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMissionState;
    private LocalDateTime stateDate;
    @Enumerated(EnumType.STRING)
    private MissionStateEnum state;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MISSION_MISSION_STATE",
            joinColumns = { @JoinColumn(name = "MISSION_STATE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "MISSION_ID") })
    private Set<Mission> missions = new HashSet<Mission>();

    public MissionState() {
    }

    public Long getIdMissionState() {
        return idMissionState;
    }

    public void setIdMissionState(Long idMissionState) {
        this.idMissionState = idMissionState;
    }

    public LocalDateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(LocalDateTime stateDate) {
        this.stateDate = stateDate;
    }

    public MissionStateEnum getState() {
        return state;
    }

    public void setState(MissionStateEnum state) {
        this.state = state;
    }

    public Set<Mission> getMissions() {
        return missions;
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }
    public void addMission(Mission mission){
        this.missions.add(mission);
    }
}
