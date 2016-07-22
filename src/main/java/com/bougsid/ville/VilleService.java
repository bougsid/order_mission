package com.bougsid.ville;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
@Service
public class VilleService implements IVilleService {
    @Autowired
    private VilleRepository villeRepository;
    private int maxSize = 10;
    @Override
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    @Override
    public Page<Ville> findAll(int page) {
        return this.villeRepository.findAll(new PageRequest(page,maxSize));
    }

    @Override
    public Ville save(Ville ville) {
        return this.villeRepository.save(ville);
    }

    @Override
    public void delete(Ville ville) {
        this.villeRepository.delete(ville);
    }

}
