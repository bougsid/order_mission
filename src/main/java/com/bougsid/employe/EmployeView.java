package com.bougsid.employe;

import com.bougsid.OrderMissionApplication;
import com.bougsid.bank.Bank;
import com.bougsid.bank.IBankService;
import com.bougsid.service.IServiceService;
import com.bougsid.service.Service;
import org.springframework.data.domain.Page;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean(name = "employeView")
@ViewScoped
public class EmployeView implements Serializable {
    private Employe employe = new Employe();
    private IEmployeService employeService;
    private IBankService bankService;
    private IServiceService serviceService;
    private Employe selectedEmploye;
    private List<Employe> employes;
    private List<Bank> banks;
    private List<Service> services;
    private int page;
    private int maxPages;

    public EmployeView() {
        //Get Banks List
        this.bankService = OrderMissionApplication.getContext().getBean(IBankService.class);
        this.employeService = OrderMissionApplication.getContext().getBean(IEmployeService.class);
        this.serviceService = OrderMissionApplication.getContext().getBean(IServiceService.class);
        this.banks = this.bankService.getAllBanks();
        this.services = this.serviceService.getAllServices();
        //Get Employes List
        this.page = 0;
        Page<Employe> employePage = this.employeService.findAll(this.page);
        this.employes = employePage.getContent();
        this.maxPages = employePage.getTotalPages();
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.employes = this.employeService.findAll(page - 1).getContent();
    }

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }

    public List<Employe> getEmployes() {

        return this.employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void saveEmploye() {
        System.out.println("Save Employe");
        this.employeService.save(selectedEmploye);
    }

    public void newEmploye() {
        this.selectedEmploye = new Employe();
    }

    public SelectItem[] getClasses() {
        SelectItem[] items = new SelectItem[EmployeClasse.values().length];
        int i = 0;
        for (EmployeClasse g : EmployeClasse.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

    public SelectItem[] getCivilities() {
        SelectItem[] items = new SelectItem[Civilite.values().length];
        int i = 0;
        for (Civilite g : Civilite.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

    public List<Bank> getBanks() {
        return this.banks;
    }
    public List<Service> getServices() {
        return this.services;
    }

    public void initPassword() {
        this.employeService.initPassword(selectedEmploye);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }
}

