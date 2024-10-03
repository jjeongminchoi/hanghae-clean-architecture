package com.hanghae.clean_architecture.infrastructure.lecture;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT l FROM Lecture l WHERE l.lectureDate BETWEEN :lectureStartDate AND :lectureEndDate")
    List<Lecture> getLecturesByDate(@Param("lectureStartDate") LocalDateTime lectureStartDate, @Param("lectureEndDate") LocalDateTime lectureEndDate);
}
