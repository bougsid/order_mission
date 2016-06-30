package com.bougsid.employe;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
public interface IEmployeService {
    Employe findEmployeByUsername(String username);

    Employe save(Employe employe);

    List<Employe> findAll();

    List<Employe> getDirectors();

    void initPassword(Employe selectedEmploye);
}
