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

