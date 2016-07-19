package com.bougsid.missiontype;

import com.bougsid.service.Dept;
import com.bougsid.service.IServiceService;
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
public class MissionTypeView implements Serializable {
    private MissionType missionType = new MissionType();
    @Autowired
    private IMissionTypeService missionTypeService;
    @Autowired
    private IServiceService serviceService;
    private MissionType selectedMissionType;
    private List<Dept> depts;
    private List<MissionType> missionTypes;
    private int page;
    private int maxPages;

    @PostConstruct
    public void init() {
//        this.serviceService = OrderMissionApplication.getContext().getBean(IServiceService.class);
//        this.employeService = OrderMissionApplication.getContext().getBean(IEmployeService.class);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        //Get Services List
        this.page = 0;
        Page<MissionType> typesPage = this.missionTypeService.findAll(this.page);
        this.missionTypes = typesPage.getContent();
        this.maxPages = typesPage.getTotalPages();
        this.depts = this.serviceService.findAll();

    }

    public void newType() {
        this.selectedMissionType = new MissionType();
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.missionTypes = this.missionTypeService.findAll(page - 1).getContent();
    }

    public void saveType() {
        this.missionTypeService.save(selectedMissionType);
    }

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i - 1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    public MissionType getSelectedMissionType() {
        return selectedMissionType;
    }

    public void setSelectedMissionType(MissionType selectedMissionType) {
        this.selectedMissionType = selectedMissionType;
    }

    public List<Dept> getDepts() {
        return depts;
    }

    public void setDepts(List<Dept> depts) {
        this.depts = depts;
    }

    public List<MissionType> getMissionTypes() {
        return missionTypes;
    }

    public void setMissionTypes(List<MissionType> missionTypes) {
        this.missionTypes = missionTypes;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

