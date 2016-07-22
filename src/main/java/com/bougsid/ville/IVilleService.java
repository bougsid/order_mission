package com.bougsid.ville;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
public interface IVilleService {
    List<Ville> getAllVilles();

    Page<Ville> findAll(int page);

    Ville save(Ville ville);

    void delete(Ville ville);
}
