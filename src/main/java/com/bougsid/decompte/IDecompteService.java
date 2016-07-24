package com.bougsid.decompte;

import com.bougsid.mission.Mission;

import java.util.List;

/**
 * Created by ayoub on 7/24/2016.
 */
public interface IDecompteService {
    List<Decompte> findAll();
    Decompte save(Decompte decompte);
    void delete(Decompte decompte);

    void setDecompteWithMission(Mission mission);

    Decompte getDecompte();

    void printDecompte(Decompte decompte);
}
