package com.bougsid.mission;

import com.bougsid.OrderMissionApplication;
import com.bougsid.transport.TransportType;
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
public class MissionView implements Serializable {
    private Mission mission = new Mission();
    private int page;
    private int maxPages;
    //    @ManagedProperty(value = "#{missionService}")
    private IMissionService missionService;

    private Mission selectedMission;
    private List<Mission> missions;

    public MissionView() {
        this.missionService = OrderMissionApplication.getContext().getBean(IMissionService.class);
        this.page = 0;
        Page<Mission> missionPage = this.missionService.findAll(this.page);
        this.missions = missionPage.getContent();
        this.maxPages = missionPage.getTotalPages();
    }

    public Mission getSelectedMission() {
        return selectedMission;
    }

    public void setSelectedMission(Mission selectedMission) {
        this.selectedMission = selectedMission;
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

    public Mission getMission() {
        mission.setEmploye(missionService.getPrincipal());
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void addMission() {
        System.out.println("Add Mission");
        System.out.println(mission);
//        mission.setIdMission(null);//Always Add New Mission
        this.missionService.save(this.mission);
        this.mission = new Mission();

    }

    public void updateMission() {
        System.out.println("Call update Mission");
        System.out.println(this.selectedMission.getStartDate());
    }

    public void updateListWithPage() {
        System.out.println("Page =" + page);
        this.missions = this.missionService.findAll(page-1).getContent();
        System.out.println(this.missions.size());
    }

    public void validateMission() {
        this.missionService.validateOrRejectMission(this.selectedMission,true);
    }

    public void rejectMission() {
        this.missionService.validateOrRejectMission(this.selectedMission,false);
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

    public SelectItem[] getPages() {
        SelectItem[] pages = new SelectItem[maxPages];
        for (int i = 1; i <= maxPages; i++) {
            pages[i-1] = new SelectItem(String.valueOf(i), String.valueOf(i));
        }
        return pages;
    }
//    public List<String> getPages(){
//        List<String> pages = new ArrayList<>();
//        boolean tooLong = maxPages>8;
//
//        for (int i = 1; i <= maxPages; i++) {
//            pages.add(String.valueOf(i));
//        }
//        return pages;
//    }
}
