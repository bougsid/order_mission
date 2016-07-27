package com.bougsid.employe;

import com.bougsid.MSG;
import com.bougsid.grade.Grade;
import com.bougsid.grade.GradeType;
import com.bougsid.grade.IGradeService;
import com.bougsid.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
        employe = this.employeRepository.save(employe);
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
        if (employe.getGrade().getType() == GradeType.CHEF||employe.getGrade().getType() == GradeType.DG) {
            employe.getDept().setChef(employe);
            this.serviceRepository.save(employe.getDept());
        }
        return this.employeRepository.save(employe);
    }


    @Override
    public List<Employe> getDirectors() {
        return null;
    }

    @Override
    public void initPassword(Employe employe) {
        employe.setPassword(employe.getMatricule());
        this.employeRepository.save(employe);
    }

    @Override
    public Employe save(Employe employe) {
        return this.employeRepository.save(employe);
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
}
