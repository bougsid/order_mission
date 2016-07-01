package com.bougsid.service;

import com.bougsid.OrderMissionApplication;
import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import org.springframework.data.domain.Page;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class ServiceView implements Serializable {
    private Service service = new Service();
    private IEmployeService employeService;
    private IServiceService serviceService;
    private Service selectedService;
    private List<Employe> employes;
    private List<Service> services;
    private int page;
    private int maxPages;

    public ServiceView() {
        this.serviceService = OrderMissionApplication.getContext().getBean(IServiceService.class);
        this.employeService = OrderMissionApplication.getContext().getBean(IEmployeService.class);
        //Get Services List
        this.page = 0;
        Page<Service> servicePage = this.serviceService.findAll(this.page);
        this.services = servicePage.getContent();
        this.maxPages = servicePage.getTotalPages();
        this.employes = this.employeService.findAll();

    }
    public void newService(){
        this.selectedService = new Service();
    }
    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.services = this.serviceService.findAll(page - 1).getContent();
    }
    public void saveService(){
        this.serviceService.save(selectedService);
    }

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

