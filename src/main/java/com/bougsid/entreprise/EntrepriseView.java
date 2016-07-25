package com.bougsid.entreprise;

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
public class EntrepriseView implements Serializable {
    private Entreprise entreprise = new Entreprise();
    @Autowired
    private IEntrepriseService entrepriseService;

    private Entreprise selectedEntreprise;
    private List<Entreprise> entreprises;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        this.entreprises = this.entrepriseService.getAllEntreprices();
    }

    public Entreprise getSelectedEntreprise() {
        return selectedEntreprise;
    }

    public void setSelectedEntreprise(Entreprise selectedEntreprise) {
        this.selectedEntreprise = selectedEntreprise;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public void saveEntreprise(){
        System.out.println("Save Entreprise");
        this.entrepriseService.save(selectedEntreprise);
    }

    public void deleteEntreprise(){
        this.entrepriseService.delete(selectedEntreprise);
    }

    public void newEntreprise(){
        this.selectedEntreprise = new Entreprise();
    }

    public List<Entreprise> getEntreprises(){
        return this.entreprises;
    }

}

