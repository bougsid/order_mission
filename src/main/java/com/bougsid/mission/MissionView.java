package com.bougsid.mission;

import com.bougsid.MSG;
import com.bougsid.decompte.IDecompteService;
import com.bougsid.employe.Employe;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.entreprise.IEntrepriseService;
import com.bougsid.grade.GradeType;
import com.bougsid.missiontype.IMissionTypeService;
import com.bougsid.missiontype.MissionType;
import com.bougsid.service.Dept;
import com.bougsid.service.IServiceService;
import com.bougsid.statistics.DateType;
import com.bougsid.statistics.StatisticsType;
import com.bougsid.transport.TransportType;
import com.bougsid.ville.IVilleService;
import com.bougsid.ville.Ville;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean
@ViewScoped
public class MissionView implements Serializable {
    @Autowired
    private MSG msg;
    @Autowired
    private IMissionService missionService;
    @Autowired
    private IVilleService villeService;
    @Autowired
    private IEntrepriseService entrepriseService;
    @Autowired
    private IMissionTypeService missionTypeService;
    @Autowired
    private IDecompteService decompteService;
    @Autowired
    private IServiceService serviceService;
    private int page;
    private int maxPages;
    private boolean mine = false;
    //    @ManagedProperty(value = "#{missionService}")

    private Mission selectedMission;
    private List<Mission> missions;
    private List<Ville> sourceVilles;
    private List<Ville> targetVilles;
    private DualListModel<Ville> villes;
    private List<Entreprise> entreprises;
    private List<Dept> depts;
    private List<MissionType> missionTypes;

    private StatisticsType filter;
    private DateType filterDate;
    private Date start;
    private Date end;

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
        this.depts = this.serviceService.findAll();
        this.missionTypes = this.missionTypeService.getAllTypes();
        this.page = 0;
        this.filter = StatisticsType.ALL;
        this.filterDate = DateType.ALL;
        this.start = Calendar.getInstance().getTime();
        this.end = Calendar.getInstance().getTime();

        Page<Mission> missionPage = this.missionService.findAll(this.page, mine);
        if (missionPage != null) {
            this.updateMission(missionPage);
        }
    }

    public Mission getSelectedMission() {
        return selectedMission;
    }

    public void setSelectedMission(Mission selectedMission) {
        this.selectedMission = selectedMission;
        this.villes.setTarget(new ArrayList<>(this.selectedMission.getVilles()));
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

//    public void updateMission() {
//        if (selectedMission != null) {
//            this.missionService.save(selectedMission);
//        }
//    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        if (this.filter == StatisticsType.ALL) {
            this.updateMission(this.missionService.findAll(page - 1, mine));
        } else {
            this.updateMission(this.missionService.getFiltredMission(filter, filterDate, page - 1,mine));
        }
        System.out.println(this.missions.size());
    }

    public void getMine() {
        this.mine = true;
        this.page = 0;
        this.updateMission(this.missionService.findAll(page, mine));

    }

    private void updateMission(Page<Mission> missionPage) {
        this.missions = missionPage.getContent();
        this.maxPages = missionPage.getTotalPages();
    }

    public void cancelMission() {
        if (selectedMission != null) {
            this.missionService.cancelMission(this.selectedMission);
        }
    }

    public void validateMission() {
        if (selectedMission != null) {
            this.missionService.validateMission(this.selectedMission);
        }
    }

    public void rejectMission() {
        if (selectedMission != null) {
            this.missionService.rejectMission(this.selectedMission);
        }
    }

    public void search() {
        if (this.filter == StatisticsType.ALL && this.filterDate == DateType.ALL)
        {
            this.updateMission(this.missionService.findAll(0, mine));
        }
        else{
            this.updateMission(this.missionService.getFiltredMission(filter, filterDate, 0,mine));
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

    public SelectItem[] getStatisticsTypes() {
        SelectItem[] items = new SelectItem[StatisticsType.values().length];
        int i = 0;
        for (StatisticsType g : StatisticsType.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

    public SelectItem[] getDateTypes() {
        SelectItem[] items = new SelectItem[DateType.values().length];
        int i = 0;
        for (DateType g : DateType.values()) {
            items[i++] = new SelectItem(g, g.getLabel());
        }
        return items;
    }

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

    public void printOrderVirement() {
        System.out.println("Printing ...");
        String fileName = this.missionService.printOrderVirement();
        if (fileName == null) {
            FacesContext.getCurrentInstance().addMessage("decompteError", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getMessage("decompte.print.error"), ""));
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            context.getExternalContext().redirect(request.getContextPath()
                    + "/download/" + fileName
                    + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy 'Heure' HH:mm"))
                    + "/xlsx"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean isChefOrDG() {
        return this.missionService.isChefOrDG();
    }

    public boolean isAutre() {
        GradeType type = this.missionService.getPrincipal().getGrade().getType();
        return type == GradeType.AUTRE;
    }

    public boolean isDaf() {
        Employe principal = this.missionService.getPrincipal();
        GradeType type = null;
        if (principal.getGrade() != null)
            type = principal.getGrade().getType();
        return type == GradeType.ASSISTANT && principal.getDept().getNom().equals("DAF");
    }

    private String send() {
        return "employes";
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

    public StatisticsType getFilter() {
        return filter;
    }

    public void setFilter(StatisticsType filter) {
        this.filter = filter;
    }

    public DateType getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(DateType filterDate) {
        this.filterDate = filterDate;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
        this.filterDate.setEnd(end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
        System.out.println("Set Start =" + start);
        this.filterDate.setStart(start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public void setMissionToDecompteIframe() {
        System.out.println("set mission iframe");
        this.decompteService.setDecompteWithMission(this.selectedMission);
        System.out.println("ok1");
    }

    public List<Ville> getSourceVilles() {
        return sourceVilles;
    }

    public void setSourceVilles(List<Ville> sourceVilles) {
        this.sourceVilles = sourceVilles;
    }

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }
}
