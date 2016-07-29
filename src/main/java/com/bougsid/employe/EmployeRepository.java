package com.bougsid.employe;

import com.bougsid.grade.GradeType;
import com.bougsid.service.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
public interface EmployeRepository extends JpaRepository<Employe,Long>{
    Employe findByMatricule(String matricule);
    @Query("select e from Employe e where e.dept = ?1 and e.grade.type in ?2")
    List<Employe> getChefAndChefA(Dept dept, List<GradeType> types);
    @Query("select e from Employe e where e.grade.type in ?1")
    List<Employe> getDGAndDGA(List<GradeType> types);
    @Query("select e from Employe e where e.grade.type = ?1")
    List<Employe> getDG(GradeType type);
//    List<Employe> findByRole(EmployeRole role);
}
