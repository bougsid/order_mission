package com.bougsid.ville;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
@Service
public class VilleService implements IVilleService {
    @Autowired
    private VilleRepository villeRepository;
    @Override
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }
}
