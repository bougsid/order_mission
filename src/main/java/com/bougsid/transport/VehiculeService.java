package com.bougsid.transport;

import com.bougsid.employe.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/22/2016.
 */
@Service
public class VehiculeService implements IVehiculeService {
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private EmployeRepository employeRepository;
    private int maxPage = 10;

    @Override
    public Vehicule save(Vehicule vehicule) {
        this.vehiculeRepository.save(vehicule);
        vehicule.getEmploye().setVehicule(vehicule);
        this.employeRepository.save(vehicule.getEmploye());
        return vehicule;
    }

    @Override
    public Page<Vehicule> findAll(int page) {
        return this.vehiculeRepository.findAll(new PageRequest(page, maxPage));
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return this.vehiculeRepository.findAll();
    }

    @Override
    public void delete(Vehicule vehicule) {
        this.vehiculeRepository.delete(vehicule);
    }
}
