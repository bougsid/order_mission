package com.bougsid.missiontype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/2/2016.
 */
@Service
public class MissionTypeService implements IMissionTypeService {

    @Autowired
    private MissionTypeRepository missionTypeRepository;
    private int maxPage = 10;

    @Override
    public List<MissionType> getAllTypes() {
        return this.missionTypeRepository.findAll();
    }

    @Override
    public Page<MissionType> findAll(int page) {
        return this.missionTypeRepository.findAll(new PageRequest(page,maxPage));
    }

    @Override
    public MissionType save(MissionType type) {
        return this.missionTypeRepository.save(type);
    }

    @Override
    public void delete(MissionType type) {
        this.missionTypeRepository.delete(type);
    }
}
