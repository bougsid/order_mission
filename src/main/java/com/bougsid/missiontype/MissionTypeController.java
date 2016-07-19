package com.bougsid.missiontype;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ayoub on 6/26/2016.
 */
@Controller
public class MissionTypeController {

    @RequestMapping(value = "/types")
    public String employesPage(){
        return "types";
    }
}
