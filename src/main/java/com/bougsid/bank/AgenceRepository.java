package com.bougsid.bank;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ayoub on 7/28/2016.
 */
public interface AgenceRepository extends JpaRepository<Agence,Long> {
    public void deleteByBankAndNom(Bank bank,String nom);
}
