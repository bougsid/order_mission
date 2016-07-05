package com.bougsid.employe;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ayoub on 7/4/2016.
 */
public interface EmployeProfileRepository extends JpaRepository<EmployeProfile,Long> {
    EmployeProfile findByType(EmployeRole employeRole);
}
