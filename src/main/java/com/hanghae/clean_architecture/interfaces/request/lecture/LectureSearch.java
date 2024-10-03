package com.hanghae.clean_architecture.interfaces.request.lecture;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureSearch {

    private LocalDateTime lectureStartDate;

    private LocalDateTime lectureEndDate;

    public LectureSearch(LocalDateTime lectureStartDate, LocalDateTime lectureEndDate) {
        this.lectureStartDate = lectureStartDate;
        this.lectureEndDate = lectureEndDate;
    }
}
