package com.bougsid.grade;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ayoub on 7/3/2016.
 */
public interface GradeRepository extends JpaRepository<Grade,Long> {
    Grade findByType(GradeType type);
}
