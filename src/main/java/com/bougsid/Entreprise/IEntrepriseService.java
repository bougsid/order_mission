package com.bougsid.entreprise;

import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
public interface IEntrepriseService {
    public List<Entreprise> getAllEntreprices();
    public Entreprise save(Entreprise entreprise);
    void delete(Entreprise entreprise);
}
