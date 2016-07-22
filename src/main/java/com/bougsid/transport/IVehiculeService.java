package com.bougsid.transport;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 7/22/2016.
 */
public interface IVehiculeService {
    Vehicule save(Vehicule vehicule);

    Page<Vehicule> findAll(int page);

    List<Vehicule> getAllVehicules();

    void delete(Vehicule vehicule);

}
