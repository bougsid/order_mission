package com.bougsid.employe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 6/26/2016.
 */
@Service
public class EmployeService implements IEmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Employe findEmployeByUsername(String username) {
        return this.employeRepository.findByMatricule(username);
    }

    @Override
    public Employe save(Employe employe) {
        return this.employeRepository.save(employe);
    }

    @Override
    public List<Employe> findAll() {
        return this.employeRepository.findAll();
    }
}
