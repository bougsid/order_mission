package com.bougsid.mission;

import com.bougsid.employe.Employe;
import com.bougsid.statistics.DateType;
import com.bougsid.statistics.StatisticsType;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ayoub on 6/23/2016.
 */

public interface IMissionService {
    Page<Mission> findAll(int page,boolean mine);


    Page<Mission> getMissionsForDG(int page);

    Page<Mission> getMissionsForCHEF(int page);

    Page<Mission> getMissionsForDAF(int page);

    Page<Mission> getMissionsForEMP(int page);

    Mission save(Mission mission);
//
//    void validateMission(Mission mission);

    boolean isChefOrDG();

    @Transactional
    void cancelMission(Mission mission);

    @Transactional
    void validateMission(Mission mission);

    @Transactional
    void rejectMission(Mission mission);

    boolean validateMissionByUuid(String uuid);

    @Transactional
    boolean rejectMissionByUuid(String uuid);

    Employe getPrincipal();

    void resendMission(Mission mission);

    boolean isRejected(Mission mission);

    void printMission(Mission mission);

    String printOrderVirement();

    Page<Mission> getFiltredMission(StatisticsType filter, DateType date, int page,boolean mine);

    Page<Mission> getFiltredMissionForEMP(StatisticsType filter, DateType date, int page);

//    void rejectMission(Mission mission);
}
