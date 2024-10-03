package com.hanghae.clean_architecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureManagerRepository extends JpaRepository<LectureManager, Long> {

    @Query("SELECT lm FROM LectureManager lm WHERE lm.lectureId = :lectureId")
    LectureManager getLectureManagerById(@Param("lectureId") Long lectureId);
}
