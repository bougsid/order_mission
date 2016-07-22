package com.bougsid.mission;

import com.bougsid.employe.Employe;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.entreprise.IEntrepriseService;
import com.bougsid.grade.GradeType;
import com.bougsid.missiontype.IMissionTypeService;
import com.bougsid.missiontype.MissionType;
import com.bougsid.transport.TransportType;
import com.bougsid.ville.IVilleService;
import com.bougsid.ville.Ville;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class MissionView implements Serializable {
    @Autowired
    private IMissionService missionService;
    @Autowired
    private IVilleService villeService;
    @Autowired
    private IEntrepriseService entrepriseService;
    @Autowired
    private IMissionTypeService missionTypeService;
    private int page;
    private int maxPages;
    //    @ManagedProperty(value = "#{missionService}")

    private Mission selectedMission;
    private List<Mission> missions;
    private List<Ville> sourceVilles;
    private List<Ville> targetVilles;
    private DualListModel<Ville> villes;
    private List<Entreprise> entreprises;
    private List<MissionType> missionTypes;
    private StreamedContent file;

    @PostConstruct
    public void init() {
//        this.missionService = OrderMissionApplication.getContext().getBean(IMissionService.class);
//        this.villeService = OrderMissionApplication.getContext().getBean(IVilleService.class);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        this.sourceVilles = this.villeService.getAllVilles();
        this.targetVilles = new ArrayList<>();
        this.villes = new DualListModel<>(sourceVilles, targetVilles);
        this.entreprises = this.entrepriseService.getAllEntreprices();
        this.missionTypes = this.missionTypeService.getAllTypes();
        this.page = 0;
        Page<Mission> missionPage = this.missionService.findAll(this.page);
        if (missionPage != null) {
            this.missions = missionPage.getContent();
            this.maxPages = missionPage.getTotalPages();
        }
    }

    public Mission getSelectedMission() {
        return selectedMission;
    }

    public void setSelectedMission(Mission selectedMission) {
        this.selectedMission = selectedMission;
        this.villes.setTarget(this.selectedMission.getVilles());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public void updateMission() {
        if (selectedMission != null) {
            this.missionService.save(selectedMission);
        }
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.missions = this.missionService.findAll(page - 1).getContent();
        System.out.println(this.missions.size());
    }

    public void validateMission() {
        if(selectedMission != null){
            this.missionService.validateMission(this.selectedMission);
        }
    }

    public void rejectMission() {
        if(selectedMission != null){
            this.missionService.rejectMission(this.selectedMission);
        }
    }

    public SelectItem[] getTransportTypes() {
        SelectItem[] items = new SelectItem[TransportType.values().length];
        int i = 0;
        for (TransportType g : TransportType.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

//    public SelectItem[] getMissionTypes() {
//        SelectItem[] items = new SelectItem[MissionType.values().length];
//        int i = 0;
//        for (MissionType g : MissionType.values()) {
//            items[i++] = new SelectItem(g, g.getLabel());
//        }
//        return items;
//    }

    public void resendMission() {
        this.missionService.resendMission(selectedMission);
    }

    public boolean isRejected() {
        if (selectedMission == null) return false;
        return this.missionService.isRejected(selectedMission);
    }

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }

    public void printMission() {
        System.out.println("Printing ...");
        this.missionService.printMission(selectedMission);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            context.getExternalContext().redirect(request.getContextPath()
                    + "/download/" + selectedMission.getUuid()
                    + "/" + selectedMission.getEmploye().getFullName()
                    + "/pdf"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printDecompte() {
        System.out.println("Printing ...");
        this.missionService.printDecompte(selectedMission);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            context.getExternalContext().redirect(request.getContextPath()
                    + "/download/" + selectedMission.getUuid()
                    + "/" + selectedMission.getEmploye().getFullName()
                    + "/xlsx"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isChefOrDG() {
        GradeType type = this.missionService.getPrincipal().getGrade().getType();
        return type == GradeType.CHEF || type == GradeType.DG;
    }
    public boolean isAutre() {
        GradeType type = this.missionService.getPrincipal().getGrade().getType();
        return type == GradeType.AUTRE;
    }
    public boolean isDaf() {
        Employe principal = this.missionService.getPrincipal();
        GradeType type = principal.getGrade().getType();
        return type == GradeType.ASSISTANT && principal.getDept().getNom().equals("DAF");
    }
    private String send() {
        return "employes";
    }

    public StreamedContent getFile() {
        return this.file;
    }

    public List<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(List<MissionType> missionTypes) {
        this.missionTypes = missionTypes;
    }

    public List<Entreprise> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public DualListModel<Ville> getVilles() {
        return villes;
    }

    public void setVilles(DualListModel<Ville> villes) {
        this.villes = villes;
    }
}
