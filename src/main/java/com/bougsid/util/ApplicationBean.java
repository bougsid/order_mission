package com.bougsid.util;

import com.bougsid.employe.Employe;
import com.bougsid.mission.IMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * Created by ayoub on 6/28/2016.
 */
@ManagedBean
@SessionScoped
public class ApplicationBean {
    @Autowired
    private IMissionService missionService;
    private Employe connectedEmploye;
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
//        this.missionService = OrderMissionApplication.getContext().getBean(IMissionService.class);
        this.connectedEmploye = this.missionService.getPrincipal();
    }

    public Employe getConnectedEmploye() {
        return connectedEmploye;
    }

    public void setConnectedEmploye(Employe connectedEmploye) {
        this.connectedEmploye = connectedEmploye;
    }
}
