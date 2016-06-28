package com.bougsid.employe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ayoub on 6/26/2016.
 */
@Controller
public class EmployeController {

    @RequestMapping(value = "/employes")
    public String employesPage(){
        return "employes";
    }
}
