package com.hanghae.clean_architecture.infrastructure.lecture;

import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureManagerRepository extends JpaRepository<LectureManager, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lm FROM LectureManager lm WHERE lm.lectureId = :lectureId")
    LectureManager getLectureManagerById(@Param("lectureId") Long lectureId);
}
