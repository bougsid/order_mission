package com.bougsid.missiontype;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 7/2/2016.
 */

public interface IMissionTypeService {
    List<MissionType> getAllTypes();
    Page<MissionType> findAll(int page);
    MissionType save(MissionType type);
    void delete(MissionType type);
}
