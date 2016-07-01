package com.bougsid.employe;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
public interface IEmployeService {
    List<Employe> findAll();
    Page<Employe> findAll(int page);

    Employe findEmployeByUsername(String username);

    Employe save(Employe employe);

    List<Employe> getDirectors();

    void initPassword(Employe selectedEmploye);
}
