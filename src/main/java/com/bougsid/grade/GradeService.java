package com.bougsid.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ayoub on 7/3/2016.
 */
@Service
public class GradeService implements IGradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Override
    public List<Grade> getAllGrades() {
        return this.gradeRepository.findAll();
    }

    @Override
    public Grade save(Grade grade) {
        return this.gradeRepository.save(grade);
    }

    @Override
    public Grade findByType(GradeType type) {
        return gradeRepository.findByType(type);
    }
}
