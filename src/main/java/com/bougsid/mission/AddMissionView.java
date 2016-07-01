package com.bougsid.mission;

import com.bougsid.entreprise.Entreprise;
import com.bougsid.entreprise.IEntrepriseService;
import com.bougsid.OrderMissionApplication;
import com.bougsid.transport.TransportType;
import com.bougsid.ville.IVilleService;
import com.bougsid.ville.Ville;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayoub on 7/1/2016.
 */
@ManagedBean
@ViewScoped
public class AddMissionView {
    private IMissionService missionService;
    private IVilleService villeService;
    private IEntrepriseService entrepriseService;
    private List<Ville> sourceVilles;
    private List<Ville> targetVilles;
    private DualListModel<Ville> villes;
    private List<Entreprise> entreprises;
    private Mission mission = new Mission();

    @PostConstruct
    public void init() {
        this.missionService = OrderMissionApplication.getContext().getBean(IMissionService.class);
        this.villeService = OrderMissionApplication.getContext().getBean(IVilleService.class);
        this.entrepriseService = OrderMissionApplication.getContext().getBean(IEntrepriseService.class);
        this.entreprises = this.entrepriseService.getAllEntreprices();
        this.sourceVilles = this.villeService.getAllVilles();
        this.targetVilles = new ArrayList<>();
        this.villes = new DualListModel<>(sourceVilles,targetVilles);
    }
    public void addMission() {
        System.out.println("Add Mission");
//        System.out.println(mission);
//        mission.setIdMission(null);//Always Add New Mission
//        this.villes.getTarget().forEach(System.out::println);
        System.out.println("Size ="+this.villes.getTarget().size());
        for (Ville ville : this.villes.getTarget()) {
            System.out.println("---------------"+ville.getId()+ville.getNom());
        }
        this.mission.setVilles(this.villes.getTarget());
        this.missionService.save(this.mission);
        this.mission = new Mission();

    }

    public SelectItem[] getTransportTypes() {
        SelectItem[] items = new SelectItem[TransportType.values().length];
        int i = 0;
        for (TransportType g : TransportType.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

    public SelectItem[] getMissionTypes() {
        SelectItem[] items = new SelectItem[MissionType.values().length];
        int i = 0;
        for (MissionType g : MissionType.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }
    public DualListModel<Ville> getVilles() {
        return villes;
    }

    public void setVilles(DualListModel<Ville> villes) {
        this.villes = villes;
    }

    public Mission getMission() {
        mission.setEmploye(missionService.getPrincipal());
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public List<Entreprise> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }
}
