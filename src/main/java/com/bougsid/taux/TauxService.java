package com.bougsid.taux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/20/2016.
 */
@Service
public class TauxService implements ITauxService {
    @Autowired
    private TauxRepository tauxRepository;

    @Override
    public Taux save(Taux taux) {
        return this.tauxRepository.save(taux);
    }

    @Override
    public Taux getTaux() {
        List<Taux> tauxes = this.tauxRepository.findAll();
        return (tauxes.size() != 0) ? tauxes.get(0) : null;
    }
}
