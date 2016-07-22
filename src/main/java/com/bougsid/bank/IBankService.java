package com.bougsid.bank;

import java.util.List;

/**
 * Created by ayoub on 6/29/2016.
 */
public interface IBankService {
    List<Bank> getAllBanks();

    void addBank(Bank bank);

    Bank findOne(Long id);

    void save(Bank bank);

    void delete(Bank bank);
}
