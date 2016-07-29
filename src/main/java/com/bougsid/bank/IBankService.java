package com.bougsid.bank;

import java.util.List;

/**
 * Created by ayoub on 6/29/2016.
 */
public interface IBankService {
    List<Bank> getAllBanks();

    List<Agence> getAllAgences();

    void addBank(Bank bank,String agences);

    Bank findOne(Long id);

    Bank save(Bank bank);
    Agence saveAgence(Agence agence);
    void deleteAgence(Agence agence);

    void delete(Bank bank);
}
