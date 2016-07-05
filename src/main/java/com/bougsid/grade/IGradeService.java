package com.bougsid.grade;

import java.util.List;

/**
 * Created by ayoub on 7/3/2016.
 */
public interface IGradeService {
    List<Grade> getAllGrades();
    Grade save(Grade grade);
    Grade findByType(GradeType type);
}
