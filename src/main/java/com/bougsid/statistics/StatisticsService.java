package com.bougsid.statistics;

import com.bougsid.mission.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ayoub on 7/25/2016.
 */
public class StatisticsService implements IStatisticsService {
    @Autowired
    private MissionRepository missionRepository;


}
