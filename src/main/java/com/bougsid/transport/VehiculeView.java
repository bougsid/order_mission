package com.bougsid.transport;

import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class VehiculeView implements Serializable {

    @Autowired
    private IVehiculeService vehiculeService;
    @Autowired
    private IEmployeService employeService;
    private Vehicule selectedVehicule;
    private List<Vehicule> vehicules;
    private List<Employe> employes;
    private int page;
    private int maxPages;
    private Vehicule vehicule;

    @PostConstruct
    public void init() {
//        this.serviceService = OrderMissionApplication.getContext().getBean(IServiceService.class);
//        this.employeService = OrderMissionApplication.getContext().getBean(IEmployeService.class);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        //Get Vehicules List
        this.page = 0;
        Page<Vehicule> servicePage = this.vehiculeService.findAll(this.page);
        this.employes = employeService.findAll();
        this.vehicules = servicePage.getContent();
        this.maxPages = servicePage.getTotalPages();
        vehicule = new Vehicule();
    }

    public void newVehicule() {
        this.selectedVehicule = new Vehicule();
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.vehicules = this.vehiculeService.findAll(page - 1).getContent();
    }

    public void saveVehicule() {
        this.vehiculeService.save(selectedVehicule);
    }

    public void deleteVehicule() {
        this.vehiculeService.delete(selectedVehicule);
    }

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Vehicule getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Vehicule selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

