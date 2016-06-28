package com.bougsid.util;

import com.bougsid.OrderMissionApplication;
import com.bougsid.employe.Employe;
import com.bougsid.mission.IMissionService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by ayoub on 6/28/2016.
 */
@ManagedBean
@SessionScoped
public class ApplicationBean {
    private IMissionService missionService;
    private Employe connectedEmploye;
    public ApplicationBean() {
        this.missionService = OrderMissionApplication.getContext().getBean(IMissionService.class);
        this.connectedEmploye = this.missionService.getPrincipal();
    }

    public Employe getConnectedEmploye() {
        return connectedEmploye;
    }

    public void setConnectedEmploye(Employe connectedEmploye) {
        this.connectedEmploye = connectedEmploye;
    }
}
