package com.bougsid.missionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/2/2016.
 */
@Service
public class MissionTypeService implements IMissionTypeService {

    @Autowired
    private MissionTypeRepository missionTypeRepository;
    @Override
    public List<MissionType> getAllTypes() {
        return this.missionTypeRepository.findAll();
    }
}
