package com.bougsid.transport;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ayoub on 7/22/2016.
 */
public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
//    public List<Vehicule> findByService(boolean service);
}
