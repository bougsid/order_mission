package com.bougsid.mission;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne
    private Mission mission;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Mission> missions = new HashSet<Mission>();

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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
//    public Set<Mission> getMissions() {
//        return missions;
//    }
//
//    public void setMissions(Set<Mission> missions) {
//        this.missions = missions;
//    }
//    public void addMission(Mission mission){
//        this.missions.add(mission);
//    }
}
