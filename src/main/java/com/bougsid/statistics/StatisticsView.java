package com.bougsid.statistics;

import org.primefaces.model.chart.PieChartModel;
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
@ManagedBean
@ViewScoped
public class StatisticsView implements Serializable {
    @Autowired
    private IStatisticsService statisticsService;
    private PieChartModel pieModel1;
    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);
        this.createPieModel1();
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);

        pieModel1.setTitle("Simple Pie");
        pieModel1.setLegendPosition("w");
    }
}

