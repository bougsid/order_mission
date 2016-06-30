package com.bougsid.bank;

import com.bougsid.OrderMissionApplication;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class BankView implements Serializable {
    private Bank bank = new Bank();
    private IBankService bankService;

    private Bank selectedBank;
    private List<Bank> banks;

    public BankView() {
        this.bankService = OrderMissionApplication.getContext().getBean(IBankService.class);
        this.banks = this.bankService.getAllBanks();
    }

    public Bank getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(Bank selectedBank) {
        this.selectedBank = selectedBank;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void saveBank(){
        System.out.println("Save Bank");
        this.bankService.save(selectedBank);
    }

    public void newBank(){
        this.selectedBank = new Bank();
    }

    public List<Bank> getBanks(){
        return this.banks;
    }

}

