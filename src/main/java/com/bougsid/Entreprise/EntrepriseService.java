package com.bougsid.entreprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
@Service
public class EntrepriseService implements IEntrepriseService {
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Override
    public List<Entreprise> getAllEntreprices() {
        return this.entrepriseRepository.findAll();
    }

    @Override
    public Entreprise save(Entreprise entreprise) {
        return this.entrepriseRepository.save(entreprise);
    }

    @Override
    public void delete(Entreprise entreprise) {
        this.entrepriseRepository.delete(entreprise);
    }
}
