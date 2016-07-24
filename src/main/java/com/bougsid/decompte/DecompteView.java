package com.bougsid.decompte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class DecompteView implements Serializable {
    private Decompte decompte;
    @Autowired
    private IDecompteService decompteService;

    private Decompte selectedDecompte;
    private List<Decompte> decomptes;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
//        this.decompteService = OrderMissionApplication.getContext().getBean(IDecompteService.class);
        this.decomptes = this.decompteService.findAll();
        this.decompte = this.decompteService.getDecompte();
    }

    public Decompte getSelectedDecompte() {
        return selectedDecompte;
    }

    public void setSelectedDecompte(Decompte selectedDecompte) {
        this.selectedDecompte = selectedDecompte;
    }

    public void setDecomptes(List<Decompte> decomptes) {
        this.decomptes = decomptes;
    }

    public Decompte getDecompte() {
        return decompte;
    }

    public void setDecompte(Decompte decompte) {
        this.decompte = decompte;
    }

    public void saveDecompte(){
        System.out.println("Save Decompte"+decompte.getImputation());
        this.decompteService.save(this.decompte);
        System.out.println("Printing ...");
        this.decompteService.printDecompte(decompte);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            context.getExternalContext().redirect(request.getContextPath()
                    + "/download/" + decompte.getMission().getUuid()
                    + "/" + decompte.getMission().getEmploye().getFullName()
                    + "/xlsx"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDecompte(){
        this.decompteService.delete(selectedDecompte);
    }

    public void newDecompte(){
        this.selectedDecompte = new Decompte();
    }

    public List<Decompte> getDecomptes(){
        return this.decomptes;
    }

}

