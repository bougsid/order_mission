package com.bougsid.employe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@Component
public class EmployeView implements Serializable {
    private Employe employe = new Employe();
    @Autowired
    private IEmployeService employeService;

    private Employe selectedEmploye;
    private List<Employe> employes;

    public Employe getSelectedEmploye() {
        return selectedEmploye;
    }

    public void setSelectedEmploye(Employe selectedEmploye) {
        this.selectedEmploye = selectedEmploye;
    }

    public List<Employe> getEmployes() {
        if (this.employes == null)
            this.employes = this.employeService.findAll();
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

    public void saveEmploye(){
        this.employeService.save(selectedEmploye);
        this.employes = this.employeService.findAll();
    }

    public void newEmploye(){
        this.selectedEmploye = new Employe();
    }

    public SelectItem[] getClasses() {
        SelectItem[] items = new SelectItem[EmployeClasse.values().length];
        int i = 0;
        for(EmployeClasse g: EmployeClasse.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }
}

