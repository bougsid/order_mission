package com.bougsid.bank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ayoub on 6/26/2016.
 */
@Controller
public class BankController {

    @RequestMapping(value = "/banks")
    public String employesPage(){
        return "banks";
    }
}
