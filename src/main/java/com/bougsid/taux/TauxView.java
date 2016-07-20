package com.bougsid.taux;

import com.bougsid.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * Created by ayoub on 6/23/2016.
 */
@ManagedBean(name = "tauxView")
@ViewScoped
public class TauxView implements Serializable {

    @Autowired
    private ITauxService tauxService;
    private Taux taux;
    @Autowired
    private MSG msg;



    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        taux = this.tauxService.getTaux();
        if(taux == null) taux = new Taux();
    }

    public void addTaux(){
        this.tauxService.save(taux);
    }
    public Taux getTaux() {
        return taux;
    }

    public void setTaux(Taux taux) {
        this.taux = taux;
    }
}

