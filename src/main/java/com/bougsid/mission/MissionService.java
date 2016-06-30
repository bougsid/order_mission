package com.bougsid.mission;

import com.bougsid.employe.Employe;
import com.bougsid.employe.EmployeProfile;
import com.bougsid.employe.EmployeRole;
import com.bougsid.employe.EmployeUserDetails;
import com.bougsid.util.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ayoub on 6/23/2016.
 */
@Service
public class MissionService implements IMissionService {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MissionStateRepository missionStateRepository;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private NotificationService notificationService;
    private final static int pageSize = 4;

    @Override
    public Page<Mission> findAll(int page) {
        EmployeRole employeRole = getRole();
        MissionStateEnum currentState = null;
        switch (employeRole) {
            case DG:
                return this.getMissionsForDG(page);
            case DE:
                return this.getMissionsForDE(page);
            case SAE:
                return this.getMissionsForSAE(page);
            case LEC:
                return this.getMissionsForLEC(page);
            case EMP:
                return this.getMissionsForEMP(page);
        }
        return null;
    }

    @Override
    public Page<Mission> getMissionsForDG(int page) {
        return this.missionRepository.findByCurrentState(MissionStateEnum.VDE, new PageRequest(page, pageSize));
    }

    @Override
    public Page<Mission> getMissionsForDE(int page) {
        //select mission where mission is current and type is DE type OR
        //select mission which already validated by SAE or LEC
        List<MissionStateEnum> states = new ArrayList<>();
        states.add(MissionStateEnum.VSAE);
        states.add(MissionStateEnum.VLEC);
        Page<Mission> missionPage = this.missionRepository.findByCurrentStateAndTypeInOrCurrentStateIn(MissionStateEnum.CURRENT,
                this.getTypesOfRole(EmployeRole.DE), states, new PageRequest(page, pageSize));
        return missionPage;
    }

    @Override
    public Page<Mission> getMissionsForSAE(int page) {
        Page<Mission> missionPage = this.missionRepository.findByCurrentStateAndTypeIn(MissionStateEnum.CURRENT,
                this.getTypesOfRole(EmployeRole.SAE), new PageRequest(page, pageSize));
        return missionPage;
    }

    @Override
    public Page<Mission> getMissionsForLEC(int page) {
        Page<Mission> missionPage = this.missionRepository.findByCurrentStateAndTypeIn(MissionStateEnum.CURRENT,
                this.getTypesOfRole(EmployeRole.LEC), new PageRequest(page, pageSize));
        return missionPage;
    }

    @Override
    public Page<Mission> getMissionsForEMP(int page) {
        Page<Mission> missionPage = this.missionRepository.findByEmploye(getPrincipal(), new PageRequest(page, pageSize));
        return missionPage;
    }

    @Override
    public Mission save(Mission mission) {
        if (mission.getCurrentState() == null)
            mission.setCurrentState(MissionStateEnum.CURRENT);
        return this.missionRepository.save(mission);
    }

    @Override
    @Transactional
    public void validateOrRejectMission(Mission mission, boolean validate) {
        MissionStateEnum missionStateEnum = getStateDependsOnConnectedPrincipal(validate);
        mission.setCurrentState(missionStateEnum);
        this.missionRepository.save(mission);
        MissionState state = context.getBean(MissionState.class);
        state.setState(missionStateEnum);
        state.setStateDate(LocalDate.now());
        state.addMission(mission);
        mission.addState(state);
        state = this.missionStateRepository.save(state);
        if (missionStateEnum == MissionStateEnum.VDE) {
            try {
                this.notificationService.sendNotificaitoin(mission);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional
    public boolean validateMissionByUuid(String uuid) {
        Mission mission = this.missionRepository.findByUuid(uuid);
        if (mission == null) return false;
        MissionState state = context.getBean(MissionState.class);
        state.setState(MissionStateEnum.VDG);
        state.setStateDate(LocalDate.now());
        state.addMission(mission);
        this.missionStateRepository.save(state);
        mission.setCurrentState(MissionStateEnum.VDG);
//        mission.addState(state);
        this.missionRepository.save(mission);
        return true;
    }

    private MissionStateEnum getStateDependsOnConnectedPrincipal(boolean validate) {
        EmployeRole employeRole = getRole();
        switch (employeRole) {
            case DE:
                return (validate) ? MissionStateEnum.VDE : MissionStateEnum.RDE;
            case DG:
                return (validate) ? MissionStateEnum.VDG : MissionStateEnum.RDG;
            case SAE:
                return (validate) ? MissionStateEnum.VSAE : MissionStateEnum.RSAE;
            case LEC:
                return (validate) ? MissionStateEnum.VLEC : MissionStateEnum.RLEC;
        }
        return null;
    }

    private EmployeRole getRole() {
        Employe employe = getPrincipal();
        for (EmployeProfile employeProfile : employe.getEmployeProfiles()) {
            return employeProfile.getType();
        }
        return null;
    }

    @Override
    public Employe getPrincipal() {
        EmployeUserDetails principal = (EmployeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getEmploye();
    }

    @Override
    public void resendMission(Mission mission) {
        mission.setCurrentState(MissionStateEnum.CURRENT);
        this.missionRepository.save(mission);
    }

    @Override
    public boolean isRejected(Mission mission) {
        MissionStateEnum currentState = mission.getCurrentState();
        switch (currentState) {
            case RDE:
            case RDG:
            case RLEC:
            case RSAE:
                return true;
        }
        return false;
    }

    private List<MissionType> getTypesOfRole(EmployeRole role) {
        return new ArrayList<MissionType>(Arrays.asList(MissionType.values())).stream().filter(v -> v.getRole().equals(role)).collect(Collectors.toList());
    }
}
