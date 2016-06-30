package com.bougsid.employe;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final static int pageSize = 4;

    @Override
    public Page<Employe> findAll(int page) {
        return this.employeRepository.findAll(new PageRequest(page, pageSize));
    }

    @Override
    public Employe findEmployeByUsername(String username) {
        return this.employeRepository.findByMatricule(username);
    }

    @Override
    public Employe save(Employe employe) {
        return this.employeRepository.save(employe);
    }


    @Override
    public List<Employe> getDirectors() {
        return this.employeRepository.findByRole(EmployeRole.DG);
    }

    @Override
    public void initPassword(Employe employe) {
        employe.setPassword(employe.getMatricule());
        this.employeRepository.save(employe);
    }
}
