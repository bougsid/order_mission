package com.bougsid.mission;

import com.bougsid.employe.Employe;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.missiontype.MissionType;
import com.bougsid.service.Dept;
import com.bougsid.ville.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findByCurrentState(MissionStateEnum currentState, Pageable page);

    Page<Mission> findByCurrentStateAndTypeIn(MissionStateEnum currentState, Collection<MissionType> types, Pageable page);

    Page<Mission> findByCurrentStateAndTypeInOrCurrentStateIn(MissionStateEnum currentState, Collection<MissionType> types, Collection<MissionStateEnum> states, Pageable page);

//    @Query("select m from Mission m where m.hierarchie = ?1 and ((m.currentState = ?2 and m.type not in ?3)or m.currentState in ?4)")
//    Page<Mission> getMissionsForRole(EmployeRole role, MissionStateEnum currentState, Collection<MissionType> types, Collection<MissionStateEnum> states, Pageable page);

//    @Query("select m from Mission m where m.employe.dept = ?1 and ((m.currentState = ?2 and m.type not in ?3)or m.currentState in ?4)")
//    Page<Mission> getMissionsForDeptChef(Dept dept, MissionStateEnum currentState, Collection<MissionType> types, Collection<MissionStateEnum> states, Pageable page);

    Mission findByUuid(String uuid);

    @Query("select m from Mission m where m.nextState = ?1")
    Page<Mission> getMissionsForDG(MissionStateEnum nextState, Pageable pageable);

    @Query("select m from Mission m where (m.nextState = ?1 and m.employe.dept = ?2) or (m.nextState = ?3 and m.type.dept= ?4)")
    Page<Mission> getMissionsForCHEF(MissionStateEnum nextStateCHEF, Dept employeDept, MissionStateEnum nextStateVDTYPE, Dept typeDept, Pageable pageable);

    Page<Mission> findByNextState(MissionStateEnum nextState, Pageable pageable);

    Page<Mission> findByNextStateIn(List<MissionStateEnum> nextState, Pageable pageable);

    Page<Mission> findByEmploye(Employe employe, Pageable page);

    Page<Mission> findByTypeAndStartDateBetween(MissionType type, LocalDateTime start, LocalDateTime end, Pageable page);

    Page<Mission> findByEntrepriseAndStartDateBetween(Entreprise entreprise, LocalDateTime start, LocalDateTime end, Pageable page);

    @Query("select m from Mission m where ?1 member m.villes and m.startDate between ?2 and ?3")
    Page<Mission> findByVilleAndStartDateBetween(Ville ville, LocalDateTime start, LocalDateTime end, Pageable page);

    @Query("select m from Mission m where m.employe.dept = ?1 and m.startDate between ?2 and ?3")
    Page<Mission> findByDeptAndStartDateBetween(Dept dept, LocalDateTime start, LocalDateTime end, Pageable page);

}
