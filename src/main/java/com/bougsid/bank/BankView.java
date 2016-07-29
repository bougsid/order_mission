package com.bougsid.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class BankView implements Serializable {
    private Bank bank = new Bank();
    @Autowired
    private IBankService bankService;

    private Bank selectedBank;
    private Agence selectedAgence;
    private List<Bank> banks;


    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
//        this.bankService = OrderMissionApplication.getContext().getBean(IBankService.class);
        this.banks = this.bankService.getAllBanks();
    }

    public Bank getSelectedBank() {
        if (selectedAgence == null) this.newAgence();
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

    public void saveBank() {
        this.bankService.save(selectedBank);
    }

    public void saveAgence() {
        if (selectedAgence.getId() == null) {
            this.selectedBank.addAgence(selectedAgence);
            this.newAgence();
        }
    }

    public void deleteBank() {
        this.bankService.delete(selectedBank);
    }

    public void newBank() {
        this.selectedBank = new Bank();
        this.newAgence();
    }

    public void newAgence() {
        this.selectedAgence = new Agence();
//        this.selectedAgence.setBank(selectedBank);
    }

    public void deleteAgence() {
        this.bankService.deleteAgence(selectedAgence);
    }

    public List<Bank> getBanks() {
        return this.banks;
    }

    public IBankService getBankService() {
        return bankService;
    }

    public void setBankService(IBankService bankService) {
        this.bankService = bankService;
    }

    public Agence getSelectedAgence() {
        return this.selectedAgence;
    }

    public void setSelectedAgence(Agence selectedAgence) {
        this.selectedAgence = selectedAgence;
    }
}

