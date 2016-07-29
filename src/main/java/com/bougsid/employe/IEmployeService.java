package com.bougsid.employe;

import com.bougsid.mission.Mission;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
public interface IEmployeService {
    List<Employe> findAll();
    Page<Employe> findAll(int page);

    Employe findEmployeByUsername(String username);

    Employe registerEmploye(Employe employe) throws MatriculeAlreadyExistException;

    void deleteEmploye(Employe employe) throws EmployeIsChefException;

    Employe updateEmploye(Employe employe) throws MatriculeAlreadyExistException;


    List<Employe> getResponsables(Mission mission);

    void initPassword(Employe selectedEmploye);

    Employe save(Employe employe);

    double updateEmployeAvoir(Employe employe, double avoir);

    void changePassword(String passwword);

    Employe getPrincipal();

    Employe getDG();
}
