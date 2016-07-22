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

    @Override
    public List<Bank> getAllBanks() {
        return this.bankRepository.findAll();
    }
    @Override
    public void addBank(Bank bank){
        this.bankRepository.save(bank);
    }

    @Override
    public Bank findOne(Long id) {
        return this.bankRepository.findOne(id);
    }

    @Override
    public void save(Bank bank) {
        this.bankRepository.save(bank);
    }

    @Override
    public void delete(Bank bank) {
        this.bankRepository.delete(bank);
    }
}
