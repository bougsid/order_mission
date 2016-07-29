package com.bougsid.employe;

import com.bougsid.MSG;
import com.bougsid.grade.Grade;
import com.bougsid.grade.GradeType;
import com.bougsid.grade.IGradeService;
import com.bougsid.mission.Mission;
import com.bougsid.service.ServiceRepository;
import com.bougsid.transport.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
@Service
public class EmployeService implements IEmployeService {

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private IGradeService gradeService;
    @Autowired
    private EmployeProfileRepository profileRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MSG msg;
    private final static int pageSize = 10;

    @Override
    public List<Employe> findAll() {
        return this.employeRepository.findAll();
    }

    @Override
    public Page<Employe> findAll(int page) {
        return this.employeRepository.findAll(new PageRequest(page, pageSize));
    }

    @Override
    public Employe findEmployeByUsername(String username) {
        return this.employeRepository.findByMatricule(username);
    }

    @Override
    public Employe registerEmploye(Employe employe) throws MatriculeAlreadyExistException {
        Employe emp = this.employeRepository.findByMatricule(employe.getMatricule());
        if (emp != null) {
            throw new MatriculeAlreadyExistException();
        }
        if (employe.getGrade().getType() == GradeType.AUTRE) {
            Grade grade = employe.getGrade();
            grade.setId(null);
            grade = this.gradeService.save(grade);
        }
        employe.setPassword(employe.getMatricule());
        EmployeProfile userProfile = this.profileRepository.findByType(EmployeRole.USER);
        employe.addEmployeProfile(userProfile);
        employe = this.save(employe);
        if (employe.getGrade().getType() == GradeType.CHEF || employe.getGrade().getType() == GradeType.DG) {
            employe.getDept().setChef(employe);
            this.serviceRepository.save(employe.getDept());
        }
        return employe;
    }

    @Override
    public void deleteEmploye(Employe employe) throws EmployeIsChefException {
        if (employe.getGrade().getType() == GradeType.CHEF) {
            throw new EmployeIsChefException();
        }
        this.employeRepository.delete(employe);
    }

    @Override
    public Employe updateEmploye(Employe employe) throws MatriculeAlreadyExistException {
        Employe emp = this.employeRepository.findByMatricule(employe.getMatricule());
        if (emp != null && emp.getIdEmploye() != employe.getIdEmploye()) {
            throw new MatriculeAlreadyExistException();
        }
        if (employe.getGrade().getType() == GradeType.CHEF || employe.getGrade().getType() == GradeType.DG) {
            employe.getDept().setChef(employe);
            this.serviceRepository.save(employe.getDept());
        }
        return this.save(employe);
    }


    @Override
    public List<Employe> getResponsables(Mission mission) {
        List<GradeType> gradeTypes = new ArrayList<>();
        switch (mission.getNextState()) {
            case VCHEF: {
                gradeTypes.add(GradeType.CHEF);
                gradeTypes.add(GradeType.CHEFA);
                return this.employeRepository.getChefAndChefA(
                        mission.getEmploye().getDept()
                        , gradeTypes);
            }
            case VDTYPE:
                gradeTypes.add(GradeType.CHEF);
                gradeTypes.add(GradeType.CHEFA);
                return this.employeRepository.getChefAndChefA(
                        mission.getType().getDept()
                        , gradeTypes);
            case VDG:
                gradeTypes.add(GradeType.DG);
                gradeTypes.add(GradeType.DGA);
                return this.employeRepository.getDGAndDGA(gradeTypes);
        }
        return null;
    }

    @Override
    public void initPassword(Employe employe) {
        employe.setPassword(employe.getMatricule());
        this.employeRepository.save(employe);
    }

    @Override
    public Employe save(Employe employe) {
        this.employeRepository.save(employe);
        if(employe.getVehicule()!=null){
            if(!employe.getVehicule().equals(employe.getVehicule().getEmploye())){
                employe.getVehicule().setEmploye(employe);
                this.vehiculeRepository.save(employe.getVehicule());
            }
        }
        return employe;
    }

    @Override
    public double updateEmployeAvoir(Employe employe, double montant) {
        if (employe.getAvoir() >= montant) {
            employe.setAvoir(employe.getAvoir() - montant);
            montant = 0.0;
        } else {
            montant -= employe.getAvoir();
            employe.setAvoir(0);
        }
        this.employeRepository.save(employe);
        return montant;
    }

    @Override
    public void changePassword(String passwword) {
        Employe employe = getPrincipal();
        employe.setPassword(passwword);
        this.employeRepository.save(employe);
    }

    @Override
    public Employe getPrincipal() {
        EmployeUserDetails principal = (EmployeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getEmploye();
    }

    @Override
    public Employe getDG(){
        List<Employe> dir = this.employeRepository.getDG(GradeType.DG);
        if(dir!=null&&dir.size()!=0) {
            return dir.get(0);
        }
        return null;
    }
}
