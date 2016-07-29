package com.bougsid.employe;

import com.bougsid.MSG;
import com.bougsid.bank.Agence;
import com.bougsid.bank.Bank;
import com.bougsid.bank.IBankService;
import com.bougsid.grade.Grade;
import com.bougsid.grade.IGradeService;
import com.bougsid.service.Dept;
import com.bougsid.service.IServiceService;
import com.bougsid.transport.IVehiculeService;
import com.bougsid.transport.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean(name = "employeView")
@ViewScoped
public class EmployeView implements Serializable {
    private Employe employe = new Employe();
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IBankService bankService;
    @Autowired
    private IVehiculeService vehiculeService;
    @Autowired
    private IServiceService serviceService;
    @Autowired
    private IGradeService gradeService;
    private Bank selectedBank;
    @Autowired
    private MSG msg;
    private Employe selectedEmploye;
    private List<Employe> employes;
    //    private List<Agence> agences;
    private List<Bank> banks;
    private List<Vehicule> vehicules;
    private List<Dept> depts;
    private List<Grade> grades;
    private int page;
    private int maxPages;


    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
//        this.agences = this.bankService.getAllAgences();
        this.banks = this.bankService.getAllBanks();
        if (this.banks.size() != 0)
            this.selectedBank = this.banks.get(0);
        this.vehicules = this.vehiculeService.getAllVehicules();
        this.depts = this.serviceService.getAllServices();
        this.grades = this.gradeService.getAllGrades();
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

    public Bank getSelectedBank() {
        return selectedBank;
    }

    public void setSelectedBank(Bank selectedBank) {
        this.selectedBank = selectedBank;
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
        try {
            if (selectedEmploye.getIdEmploye() == null) {
                this.employeService.registerEmploye(selectedEmploye);
            } else
                this.employeService.updateEmploye(selectedEmploye);
        } catch (MatriculeAlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getMessage("error.employe.matriculeAlreadyExistException"), msg.getMessage("error.employe.matriculeAlreadyExistException")));
        }
    }

    public void newEmploye() {
        System.out.println("New Employe");
        this.selectedEmploye = new Employe();
    }

    public void deleteEmploye() {
        if (selectedEmploye != null)
            try {
                this.employeService.deleteEmploye(selectedEmploye);
            } catch (EmployeIsChefException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        msg.getMessage("error.employe.employeIsChef"),
                        msg.getMessage("error.employe.employeIsChef")));
                e.printStackTrace();
            }
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

    public Set<Agence> getAgences() {
        if (selectedBank != null)
            return this.selectedBank.getAgences();
        else return new HashSet<>();
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public List<Dept> getDepts() {
        return this.depts;
    }

    public List<Grade> getGrades() {
        return grades;
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

