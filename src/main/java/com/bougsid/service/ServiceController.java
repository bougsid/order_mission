package com.bougsid.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ayoub on 6/26/2016.
 */
@Controller
public class ServiceController {

    @RequestMapping(value = "/services")
    public String employesPage(){
        return "services";
    }
}
