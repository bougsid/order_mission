package com.bougsid.employe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
public interface EmployeRepository extends JpaRepository<Employe,Long>{
    Employe findByMatricule(String matricule);

    List<Employe> findByRole(EmployeRole role);
}
