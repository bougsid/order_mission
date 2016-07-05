package com.bougsid.service;

import com.bougsid.grade.Grade;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ayoub on 6/30/2016.
 */
public interface IServiceService {
    Page<Dept> findAll(int page);

    List<Dept> getAllServices();

    Dept save(Dept dept);

    Grade getChefGrade();
}
