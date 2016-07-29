package com.bougsid.mission;

import com.bougsid.decompte.Excel;
import com.bougsid.employe.*;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.grade.GradeType;
import com.bougsid.missiontype.MissionType;
import com.bougsid.printers.OrderVirementPrinter;
import com.bougsid.printers.PrintMission;
import com.bougsid.printers.StatisticsPrinter;
import com.bougsid.service.Dept;
import com.bougsid.statistics.DateType;
import com.bougsid.statistics.StatisticsType;
import com.bougsid.util.NotificationService;
import com.bougsid.ville.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ayoub on 6/23/2016.
 */
@Service
public class MissionService implements IMissionService {
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private MissionStateRepository missionStateRepository;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PrintMission printMission;
    @Autowired
    private Excel excel;
    @Autowired
    private OrderVirementPrinter orderVirementPrinter;
    @Autowired
    private StatisticsPrinter statisticsPrinter;
    private final static int pageSize = 10;

    @Override
    public Page<Mission> findAll(int page, boolean mine) {
        if (mine) return this.getMissionsForEMP(page);
        Employe cp = getPrincipal();
        if (cp.getGrade() != null)
            switch (cp.getGrade().getType()) {
                case DG:
                case DGA:
                    return this.getMissionsForDG(page);
                case CHEF:
                case CHEFA:
                    return this.getMissionsForCHEF(page);
                case ASSISTANT: {
                    if (cp.getDept().getNom().equals("DAF"))
                        return this.getMissionsForDAF(page);
                    return this.getMissionsForEMP(page);
                }
                case AUTRE:
                    return this.getMissionsForEMP(page);
            }
        return null;
    }

    @Override
    public Page<Mission> getMissionsForDG(int page) {
        return this.missionRepository.getMissionsForDG(MissionStateEnum.VDG, new PageRequest(page, pageSize));
    }

    @Override
    public Page<Mission> getMissionsForCHEF(int page) {
        Employe ce = getPrincipal();
        return this.missionRepository.getMissionsForCHEF(MissionStateEnum.VCHEF, ce.getDept(), MissionStateEnum.VDTYPE, ce.getDept(), new PageRequest(page, pageSize));
    }

    @Override
    public Page<Mission> getMissionsForDAF(int page) {
//        Employe ce = getPrincipal();
        List<MissionStateEnum> nextStates = new ArrayList<>();
        nextStates.add(MissionStateEnum.DAF);
        nextStates.add(MissionStateEnum.VALIDATED);
        return this.missionRepository.findByNextStateInOrderByIdMissionDesc(nextStates, new PageRequest(page, pageSize));
    }

    @Override
    public Page<Mission> getMissionsForEMP(int page) {
        Page<Mission> missionPage = this.missionRepository.findByEmployeOrderByIdMissionDesc(getPrincipal(), new PageRequest(page, pageSize));
        return missionPage;
    }

    @Override
    public Mission save(Mission mission) {
        if (mission.getCurrentState() == null) {
            mission.setCurrentState(MissionStateEnum.CURRENT);
            mission.setNextState(getNextState(mission));
            mission = this.missionRepository.save(mission);
            MissionState state = context.getBean(MissionState.class);
            state.setState(MissionStateEnum.CURRENT);
            state.setStateDate(LocalDateTime.now());
//            state.addMission(mission);
            state.setMission(mission);
            state = this.missionStateRepository.save(state);
            try {
                this.notificationService.sendNotificaitoin(mission);
            } catch (InterruptedException | MessagingException e) {
                e.printStackTrace();
            }
        } else {
            mission = this.missionRepository.save(mission);
        }
        return mission;
    }

    @Override
    public boolean isChefOrDG() {
        return isChef(getPrincipal()) || isDG(getPrincipal());
    }

    public boolean isEmpChefOrDG(Employe employe) {
        return isChef(employe) || isDG(employe);
    }

    private boolean isChef(Employe employe) {
        GradeType type = employe.getGrade().getType();
        return type == GradeType.CHEF;
    }

    private boolean isDG(Employe employe) {
        GradeType type = employe.getGrade().getType();
        return type == GradeType.DG;
    }

    private MissionStateEnum getNextStateForChefOrDG(Mission mission) {
        Employe employe = mission.getEmploye();
        Dept typeDept = null;
        if (mission.getType() != null)
            typeDept = mission.getType().getDept();
        MissionStateEnum nextState = MissionStateEnum.CURRENT;
        if (isChef(employe)) {
            switch (mission.getCurrentState()) {
                case CURRENT: {
                    if (typeDept != null) {
                        nextState = MissionStateEnum.VDTYPE;
                    } else {
                        nextState = MissionStateEnum.VDG;
                    }
                }
                break;
                case VDTYPE: {
                    nextState = MissionStateEnum.VDG;
                }
                break;
                case VDG: {
                    nextState = MissionStateEnum.DAF;
                }
                break;
                case DAF: {
                    nextState = MissionStateEnum.VALIDATED;
                }
                break;
            }
        } else {
            switch (mission.getCurrentState()) {
                case CURRENT: {
                    if (typeDept != null) {
                        nextState = MissionStateEnum.VDTYPE;
                    } else {
                        nextState = MissionStateEnum.DAF;
                    }
                }
                break;
                case VDTYPE: {
                    nextState = MissionStateEnum.DAF;
                }
                break;
                case DAF: {
                    nextState = MissionStateEnum.VALIDATED;
                }
            }
        }
        return nextState;
    }

    private MissionStateEnum getNextState(Mission mission) {
        if (isEmpChefOrDG(mission.getEmploye())) return getNextStateForChefOrDG(mission);
        Dept typeDept = null;
        if (mission.getType() != null)
            typeDept = mission.getType().getDept();

        MissionStateEnum nextState = MissionStateEnum.CURRENT;
//        if (mission.getType().getDept() == null) {
        switch (mission.getCurrentState()) {
            case CURRENT: {
                if (typeDept != null) {
                    System.out.println(typeDept.getId());
                    if (typeDept.getId() == mission.getEmploye().getDept().getId())
                        nextState = MissionStateEnum.VCHEF;
                    else
                        nextState = MissionStateEnum.VDTYPE;

                } else {
                    if (mission.getEmploye().getDept() != null) {
                        nextState = MissionStateEnum.VCHEF;
                    } else {
                        nextState = MissionStateEnum.VDG;
                    }
                }
            }
            break;
            case VDTYPE: {
                if (mission.getEmploye().getDept() != null) {
                    nextState = MissionStateEnum.VCHEF;
                } else {
                    nextState = MissionStateEnum.VDG;
                }
            }
            break;
            case VCHEF: {
                nextState = MissionStateEnum.VDG;
            }
            break;
            case VDG: {
                nextState = MissionStateEnum.DAF;
            }
            break;
            case DAF:
                nextState = MissionStateEnum.VALIDATED;
                break;
        }
        return nextState;
    }


    @Override
    @Transactional
    public void cancelMission(Mission mission) {
        mission.setCurrentState(MissionStateEnum.CANCELED);
        this.missionRepository.save(mission);
        MissionState state = context.getBean(MissionState.class);
        state.setState(mission.getCurrentState());
        state.setStateDate(LocalDateTime.now());
//        state.addMission(mission);
        state.setMission(mission);
        mission.addState(state);
        state = this.missionStateRepository.save(state);
        Employe employe = mission.getEmploye();
        employe.setAvoir(employe.getAvoir() + mission.getDecompte().getTotal());
        this.employeRepository.save(employe);
    }

    @Override
    @Transactional
    public void validateMission(Mission mission) {
        mission.setCurrentState(mission.getNextState());
        mission.setNextState(this.getNextState(mission));
        mission.setSecret(UUID.randomUUID().toString());
        this.missionRepository.save(mission);
        MissionState state = context.getBean(MissionState.class);
        state.setState(mission.getCurrentState());
        state.setStateDate(LocalDateTime.now());
//        state.addMission(mission);
        state.setMission(mission);
        mission.addState(state);
        state = this.missionStateRepository.save(state);
        try {
            this.notificationService.sendNotificaitoin(mission);
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void rejectMission(Mission mission) {
        MissionStateEnum rejectState = getRStateDependsOnConnectedPrincipal(mission);
        mission.setCurrentState(rejectState);
        mission.setNextState(MissionStateEnum.CURRENT);
        mission.setSecret(UUID.randomUUID().toString());
        this.missionRepository.save(mission);
        MissionState state = context.getBean(MissionState.class);
        state.setState(mission.getCurrentState());
        state.setStateDate(LocalDateTime.now());
//        state.addMission(mission);
        state.setMission(mission);
        mission.addState(state);
        state = this.missionStateRepository.save(state);
//        if (missionStateEnum == MissionStateEnum.VDE) {
//            try {
//                this.notificationService.sendNotificaitoin(mission);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    @Transactional
    public boolean validateMissionByUuid(String uuid, String secret) {
        Mission mission = this.missionRepository.findByUuid(uuid);
        if (mission == null) return false;
        if (!mission.getSecret().equals(secret)) return false;
//        MissionState state = context.getBean(MissionState.class);
//        state.setState(mission.getCurrentState());
//        state.setStateDate(LocalDateTime.now());
//        state.addMission(mission);
//        this.missionStateRepository.registerEmploye(state);
//        mission.setCurrentState(mission.getNextState());
//        mission.setNextState(mission.getCurrentState());
//        this.missionRepository.registerEmploye(mission);
        this.validateMission(mission);
        return true;
    }

    @Override
    @Transactional
    public boolean rejectMissionByUuid(String uuid, String secret) {
        Mission mission = this.missionRepository.findByUuid(uuid);
        if (mission == null) return false;
        if (!mission.getSecret().equals(secret)) return false;

        this.rejectMission(mission);
        return true;
    }

    private MissionStateEnum getRStateDependsOnConnectedPrincipal(Mission mission) {
        switch (mission.getNextState()) {
            case VCHEF:
                return MissionStateEnum.RCHEF;
            case VDG:
                return MissionStateEnum.RDG;
            case VDTYPE:
                return MissionStateEnum.RDTYPE;
        }
//        Employe employe = getPrincipal();
//        switch (employe.getGrade().getType()) {
//            case DG:
//            case DGA:
//                return MissionStateEnum.RDG;
//            case CHEF:
//            case CHEFA: {
//                if (mission.getNextState() == MissionStateEnum.VCHEF) {
//                    return MissionStateEnum.RCHEF;
//                } else {
//                    return MissionStateEnum.RDTYPE;
//                }
//            }
//        }
        return null;
    }

    private EmployeRole getRoleOfPrincipal() {
        Employe employe = getPrincipal();
        for (EmployeProfile employeProfile : employe.getEmployeProfiles()) {
            return employeProfile.getType();
        }
        return null;
    }

//    private Dept isConnectedDeptChef() {
//        Employe employe = getPrincipal();
//        if (employe.getDept().getChef().equals(employe)) {
//            return employe.getDept();
//        }
//        return null;
//    }

    @Override
    public Employe getPrincipal() {
        EmployeUserDetails principal = (EmployeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getEmploye();
    }

    @Override
    public void resendMission(Mission mission) {
        mission.setCurrentState(MissionStateEnum.CURRENT);
        mission.setNextState(this.getNextState(mission));
        mission.setComment("");
        mission = this.missionRepository.save(mission);
        MissionState state = context.getBean(MissionState.class);
        state.setState(mission.getCurrentState());
        state.setStateDate(LocalDateTime.now());
//        state.addMission(mission);
        state.setMission(mission);
        state = this.missionStateRepository.save(state);
        try {
            this.notificationService.sendNotificaitoin(mission);
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRejected(Mission mission) {
        MissionStateEnum currentState = mission.getCurrentState();
        if (currentState != null)
            switch (currentState) {
                case RCHEF:
                case RDG:
                case RDTYPE:
                    return true;
            }
        return false;
    }

    @Override
    public void printMission(Mission mission) {
        this.printMission.printMission(mission);
    }

    @Override
    public String printOrderVirement() {
        List<Mission> missions = this.missionRepository.findByNextStateOrderByIdMissionDesc(MissionStateEnum.DAF);
        if (missions.size() != 0) {
            for (Mission mission : missions) {
                if (mission.getDecompte() == null)
                    return null;
            }
            return this.orderVirementPrinter.printOrderVirement(missions);
        }
        return null;
    }

    @Override
    public Page<Mission> getFiltredMission(StatisticsType filter, DateType date, int page, boolean mine) {
        if (mine) return this.getFiltredMissionForEMP(filter, date, page);
        LocalDateTime start = LocalDateTime.of(date.getStart(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date.getEnd(), LocalTime.MAX);
        System.out.println("min=" + start);
        System.out.println("max=" + end);
        List<MissionStateEnum> nextStates = new ArrayList<>();
        nextStates.add(MissionStateEnum.DAF);
        nextStates.add(MissionStateEnum.VALIDATED);
        switch (filter) {
            case ALL: {
                return this.missionRepository.findByNextStateInAndStartDateBetween(nextStates, start, end, new PageRequest(page, pageSize));
            }
            case TYPE: {
                MissionType type = (MissionType) filter.getCriteria();
                return this.missionRepository.findByNextStateInAndTypeAndStartDateBetween(nextStates, type, start, end, new PageRequest(page, pageSize));
            }
            case ENTREPRISE: {
                Entreprise entreprise = (Entreprise) filter.getCriteria();
                return this.missionRepository.findByNextStateInAndEntrepriseAndStartDateBetween(nextStates, entreprise, start, end, new PageRequest(page, pageSize));
            }
            case SERVICE: {
                Dept dept = (Dept) filter.getCriteria();
                return this.missionRepository.findByNextStateInAndDeptAndStartDateBetween(nextStates, dept, start, end, new PageRequest(page, pageSize));
            }
            case VILLE: {
                Ville ville = (Ville) filter.getCriteria();
                return this.missionRepository.findByNextStateInAndVilleAndStartDateBetween(nextStates, ville, start, end, new PageRequest(page, pageSize));
            }
        }
        return null;
    }

    @Override
    public Page<Mission> getFiltredMissionForEMP(StatisticsType filter, DateType date, int page) {
        LocalDateTime start = LocalDateTime.of(date.getStart(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date.getEnd(), LocalTime.MAX);
        System.out.println("min=" + start);
        System.out.println("max=" + end);
        switch (filter) {
            case ALL: {
                return this.missionRepository.findByEmployeAndStartDateBetween(getPrincipal(), start, end, new PageRequest(page, pageSize));
            }
            case TYPE: {
                MissionType type = (MissionType) filter.getCriteria();
                return this.missionRepository.findByEmployeAndTypeAndStartDateBetween(getPrincipal(), type, start, end, new PageRequest(page, pageSize));
            }
            case ENTREPRISE: {
                Entreprise entreprise = (Entreprise) filter.getCriteria();
                return this.missionRepository.findByEmployeAndEntrepriseAndStartDateBetween(getPrincipal(), entreprise, start, end, new PageRequest(page, pageSize));
            }
            case SERVICE: {
                Dept dept = (Dept) filter.getCriteria();
                return this.missionRepository.findByEmployeAndDeptAndStartDateBetween(getPrincipal(), dept, start, end, new PageRequest(page, pageSize));
            }
            case VILLE: {
                Ville ville = (Ville) filter.getCriteria();
                return this.missionRepository.findByEmployeAndVilleAndStartDateBetween(getPrincipal(), ville, start, end, new PageRequest(page, pageSize));
            }
        }
        return null;
    }

    @Override
    public String download() {
        List<Mission> missions = this.missionRepository.findByNextStateOrderByIdMissionDesc(MissionStateEnum.VALIDATED);
        if (missions.size() != 0) {
            for (Mission mission : missions) {
                if (mission.getDecompte() == null)
                    return null;
            }
            return this.statisticsPrinter.printStatistics(missions);
        }
        return null;
    }

    @Override
    public String download(StatisticsType filter, DateType date) {
        LocalDateTime start = LocalDateTime.of(date.getStart(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date.getEnd(), LocalTime.MAX);
        System.out.println("min=" + start);
        System.out.println("max=" + end);
        List<MissionStateEnum> nextStates = new ArrayList<>();
        List<Mission> missions = null;
//        nextStates.add(MissionStateEnum.DAF);
        nextStates.add(MissionStateEnum.VALIDATED);
        switch (filter) {
            case ALL: {
                missions = this.missionRepository.findByNextStateInAndStartDateBetween(nextStates, start, end);
            }
            break;
            case TYPE: {
                MissionType type = (MissionType) filter.getCriteria();
                missions = this.missionRepository.findByNextStateInAndTypeAndStartDateBetween(nextStates, type, start, end);
            }
            break;
            case ENTREPRISE: {
                Entreprise entreprise = (Entreprise) filter.getCriteria();
                missions = this.missionRepository.findByNextStateInAndEntrepriseAndStartDateBetween(nextStates, entreprise, start, end);
            }
            break;
            case SERVICE: {
                Dept dept = (Dept) filter.getCriteria();
                missions = this.missionRepository.findByNextStateInAndDeptAndStartDateBetween(nextStates, dept, start, end);
            }
            break;
            case VILLE: {
                Ville ville = (Ville) filter.getCriteria();
                missions = this.missionRepository.findByNextStateInAndVilleAndStartDateBetween(nextStates, ville, start, end);
            }
            break;
        }
        if (missions.size() != 0) {
            for (Mission mission : missions) {
                if (mission.getDecompte() == null)
                    return null;
            }
            return this.statisticsPrinter.printStatistics(missions, filter, date);
        }
        return null;
    }
}
