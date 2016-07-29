package com.bougsid.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 6/29/2016.
 */
@Service
public class BankService implements IBankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AgenceRepository agenceRepository;

    @Override
    public List<Bank> getAllBanks() {
        return this.bankRepository.findAll();
    }

    @Override
    public List<Agence> getAllAgences() {
        return this.agenceRepository.findAll();
    }

    @Override
    public void addBank(Bank bank, String agences) {
        this.bankRepository.save(bank);
    }

    @Override
    public Bank findOne(Long id) {
        return this.bankRepository.findOne(id);
    }

    @Override
    public Bank save(Bank bank) {
        return this.bankRepository.save(bank);
    }

    @Override
    public Agence saveAgence(Agence agence) {
        return this.agenceRepository.save(agence);
    }

    @Override
    public void deleteAgence(Agence agence) {
        this.agenceRepository.delete(agence);
    }

    @Override
    public void delete(Bank bank) {
        this.bankRepository.delete(bank);
    }


}
