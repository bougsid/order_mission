package com.bougsid.mission;

import com.bougsid.MSG;
import com.bougsid.employe.EmployeRole;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ayoub on 6/28/2016.
 */
public enum MissionType {
    PROSPECTION(EmployeRole.SAE),
    REUNION(EmployeRole.SAE),
    ACCOMP(EmployeRole.SAE),
    FC(EmployeRole.SAE),
    DIAGN_STRATG(EmployeRole.SAE),
    INGEN(EmployeRole.SAE),
    FORUM(EmployeRole.DE);

    private String label;
    private EmployeRole role;
    @Autowired
    private MSG msg;

    MissionType(EmployeRole role) {
        this.msg = new MSG();
        this.label = msg.getMessage("mission.type."+this.name());
        this.role = role;
    }

    public String getLabel() {
        return label;
    }

    public EmployeRole getRole() {
        return role;
    }
}
