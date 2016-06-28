package com.bougsid.mission;

import com.bougsid.employe.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by ayoub on 6/23/2016.
 */
public interface MissionRepository extends JpaRepository<Mission,Long> {
    Page<Mission> findByCurrentState(MissionStateEnum currentState, Pageable page);
    Page<Mission> findByCurrentStateAndTypeIn(MissionStateEnum currentState, Collection<MissionType> types, Pageable page);
    Page<Mission> findByCurrentStateAndTypeInOrCurrentStateIn(MissionStateEnum currentState, Collection<MissionType> types,Collection<MissionStateEnum> states, Pageable page);

    Page<Mission> findByEmploye(Employe employe,Pageable page);

}
