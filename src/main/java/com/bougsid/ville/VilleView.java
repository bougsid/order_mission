package com.bougsid.ville;

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
public class VilleView implements Serializable {
    private Ville ville = new Ville();
    @Autowired
    private IVilleService villeService;

    private Ville selectedVille;
    private List<Ville> villes;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
//        this.villeService = OrderMissionApplication.getContext().getBean(IVilleService.class);
        this.villes = this.villeService.getAllVilles();
    }

    public Ville getSelectedVille() {
        return selectedVille;
    }

    public void setSelectedVille(Ville selectedVille) {
        this.selectedVille = selectedVille;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public void saveVille(){
        System.out.println("Save Ville");
        this.villeService.save(selectedVille);
    }

    public void deleteVille(){
        this.villeService.delete(selectedVille);
    }

    public void newVille(){
        this.selectedVille = new Ville();
    }

    public List<Ville> getVilles(){
        return this.villes;
    }

}

