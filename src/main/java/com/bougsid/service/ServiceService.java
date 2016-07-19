package com.bougsid.service;

import com.bougsid.employe.IEmployeService;
import com.bougsid.grade.Grade;
import com.bougsid.grade.GradeType;
import com.bougsid.grade.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by ayoub on 6/30/2016.
 */
@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IGradeService gradeService;
    private final static int pageSize = 10;

    @Override
    public Page<Dept> findAll(int page) {
        return this.serviceRepository.findAll(new PageRequest(page, pageSize));
    }

    @Override
    public List<Dept> findAll() {
        return this.serviceRepository.findAll();
    }

    @Override
    public List<Dept> getAllServices() {
        return this.serviceRepository.findAll();
    }

    @Override
    public Dept save(Dept dept) {
        if(dept.getChef() != null){
            dept.getChef().setGrade(getChefGrade());
            dept.getChef().setDept(dept);
        }
        this.serviceRepository.save(dept);
        this.employeService.save(dept.getChef());
        return dept;
    }
    @Override
    public Grade getChefGrade(){
        return this.gradeService.findByType(GradeType.CHEF);
    }
}
